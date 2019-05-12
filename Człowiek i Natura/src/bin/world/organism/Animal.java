package bin.world.organism;

import bin.enums.Directions;
import bin.enums.Species;
import bin.interfaces.InteractionsActive;
import bin.interfaces.InteractionsPassive;
import bin.system.Pair;
import bin.world.WorldSPI;

import java.util.concurrent.ThreadLocalRandom;

import static bin.enums.OrganismType.*;
import static bin.enums.Species.*;
import static bin.enums.Values.*;
//TODO COMMENTS
public class Animal extends Mob implements InteractionsPassive, InteractionsActive {

    private Pair<Integer, Integer> oldCoords;

    public Animal(int worldID, Species specimen, Pair<Integer, Integer> coords) {
        super(worldID, specimen, coords);
    }

    @Override
    public void interact(Organism interacted) {
        if(this.specimen == interacted.getSpecies()) {this.multiply(); this.setCoords(this.oldCoords);}
        else if(this.type == PREDATOR)
        {
            if(interacted.getType() != PLANT || interacted.getSpecies() == FLOWER) {

                if (this.strength > interacted.getValue(STRENGTH)) { interacted.die(); WorldSPI.setField(this.worldID,
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
                    WorldSPI.setField(this.worldID, this.coordinates, this);
                }
                else if(this.specimen == CYBERSHEEP && interacted.getSpecies() == HOGWEED) {
                    interacted.die();
                    WorldSPI.setField(this.worldID, this.coordinates, this);
                }
                else this.setCoords(this.oldCoords);
            }
            else this.setCoords(oldCoords);
        }
    }

    @Override
    public void move() {
        this.oldCoords = this.coordinates;
        this.setCoords(WorldSPI.getCoordsInDirection(this.worldID,
                Directions.values()[ThreadLocalRandom.current().nextInt(Directions.values().length)],
                this.coordinates)); //get coordinates in random direction from enum Directions, then move
        if(WorldSPI.getField(this.worldID, this.coordinates) != null) {
            this.interact(WorldSPI.getField(this.worldID, this.coordinates));
        }
        else {
            WorldSPI.setField(this.worldID, this.oldCoords, null);
            WorldSPI.setField(this.worldID, this.coordinates, this);
        }
    }

    @Override
    public void multiply() {

        Pair <Integer,Integer> newCoords = new Pair<>(0,0);

        for(int i = 0; i < 6; ++i){
            if(WorldSPI.getField(this.worldID, WorldSPI.getCoordsInDirection(this.worldID, Directions.values()[i],this.coordinates)) == null ){
                newCoords = WorldSPI.getCoordsInDirection(this.worldID, Directions.values()[i],this.coordinates); break;}
        }

        WorldSPI.makeOrganism(this.worldID, this.specimen, newCoords);
    }
}
