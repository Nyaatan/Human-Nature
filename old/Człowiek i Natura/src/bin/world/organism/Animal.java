package bin.world.organism;

import lib.Enums;
import lib.Pair;
import bin.system.API;

import java.util.concurrent.ThreadLocalRandom;

import static lib.Enums.OrganismType.*;
import static lib.Enums.Species.AllSpecies.*;
import static lib.Enums.Values.STRENGTH;


//TODO COMMENTS
public class Animal extends Mob{

    public Animal(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID) {
        super(worldID, specimen, coords, ID);
    }
    public Animal(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(worldID, specimen, coords);
    }

    @Override
    public void interact(Organism interacted) {

        if(this.specimen == interacted.getSpecies()) {this.multiply(); this.setCoords(this.oldCoords);}

        else if(this.type == PREDATOR)
        {
            if(interacted.getType() != PLANT || interacted.getSpecies() == FLOWER) {

                if (this.strength > interacted.getValue(STRENGTH)) { interacted.die(); API.worldSPI.setField(this.worldID,
                        this.coordinates, this); }
                else this.die();
            }

            else if(interacted.getSpecies() == HOGWEED)
            {
                if(this.strength <= interacted.getValue(STRENGTH)) this.die();
                else this.setCoords(this.oldCoords);
            }

            else this.setCoords(this.oldCoords);

        }

        else if (this.type == PREY)
        {
            if(interacted.getType() == PLANT)
            {
                if(this.strength >= interacted.getValue(STRENGTH)) {
                    interacted.die();
                    API.worldSPI.setField(this.worldID, this.coordinates, this);
                }

                else if(this.specimen == CYBERSHEEP && interacted.getSpecies() == HOGWEED) {
                    interacted.die();
                    API.worldSPI.setField(this.worldID, this.coordinates, this);
                }

                else this.setCoords(this.oldCoords);
            }

            else this.setCoords(oldCoords);
        }
    }

    @Override
    public void move() {
        this.age++;

        this.oldCoords = this.coordinates;

        this.setCoords(API.worldSPI.getCoordsInDirection(this.worldID,
                Enums.Directions.values()[ThreadLocalRandom.current().nextInt(Enums.Directions.values().length)],
                this.coordinates)); //get coordinates in random direction from enum Directions, then move

        if(API.worldSPI.getSector(this.worldID, this.coordinates).getID().equals(this.sectorID)) {
            if (API.worldSPI.getField(this.worldID, this.coordinates) != null) {
                this.interact(API.worldSPI.getField(this.worldID, this.coordinates));
            } else {
                API.worldSPI.setField(this.worldID, this.oldCoords, null);
                API.worldSPI.setField(this.worldID, this.coordinates, this);
            }
        }
        else
        {
            API.worldSPI.getSector(this.worldID, this.coordinates).addVisitor(this);
            this.sectorID = API.worldAPI.getMap(this.worldID).getSectorByCoords(this.coordinates).getID();
            API.worldSPI.setField(this.worldID, this.oldCoords, null);
        }
    }

    @Override
    public void multiply() {

        Pair <Integer,Integer> newCoords = new Pair<>(0,0);

        for(int i = 0; i < 6; ++i){
            if(API.worldSPI.getField(this.worldID, API.worldSPI.getCoordsInDirection(
                    this.worldID, Enums.Directions.values()[i],this.coordinates, this.sectorID)) == null )
            {
                newCoords = API.worldSPI.getCoordsInDirection(this.worldID, Enums.Directions.values()[i],this.coordinates, this.sectorID);
                break;
            }
        }

        Organism.create(this.worldID, this.specimen, newCoords);
    }
}
