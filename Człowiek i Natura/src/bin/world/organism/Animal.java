package bin.world.organism;

import lib.API;
import lib.Enums;
import lib.Pair;

import java.util.concurrent.ThreadLocalRandom;

import static lib.Enums.OrganismType.PLANT;
import static lib.Enums.Species.AllSpecies.*;
import static lib.Enums.Values.AGE;
import static lib.Enums.Values.STRENGTH;


//TODO COMMENTS
public class Animal extends Mob{

    public Animal(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID) {
        super(specimen, coords, ID);
    }
    public Animal(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);
    }

    @Override
    public void interact(Organism interacted) {

        if(this.specimen == interacted.getSpecies()) {
            if(interacted.getValue(AGE)>3 && daysSinceMultiply>10)
            {
                interacted.multiply();
                this.daysSinceMultiply = 0;
                this.multiply();
                this.setCoords(this.oldCoords);
            }
        }

        else switch (this.type)
        {
            case PREDATOR:
                fight(interacted);
                break;

            case PREY:
                eat(interacted);
                break;
        }
    }

    @Override
    public void move() {
        this.age++;
        this.daysSinceMultiply++;
        this.oldCoords = this.coordinates;

        departThisWorld();

        this.setCoords(API.worldSPI.getCoordsInDirection(
                Enums.Directions.values()[ThreadLocalRandom.current().nextInt(Enums.Directions.values().length)],
                this.coordinates)); //get coordinates in random direction from enum Directions, then move

        try {
            if (API.worldSPI.getSector(this.coordinates).getID().equals(this.sectorID)) {
                if (API.worldSPI.getField(this.coordinates) != null) {
                    this.interact(API.worldSPI.getField(this.coordinates));
                } else {
                    API.worldSPI.setField(this.oldCoords, null);
                    API.worldSPI.setField(this.coordinates, this);
                }
            } else {
                API.worldAPI.getMap().addTransfer(this);
                this.sectorID = API.worldAPI.getMap().getChunkByCoords(this.coordinates).getID();
                API.worldSPI.setField(this.oldCoords, null);
            }
        } catch (Exception e) { }
    }

    @Override
    protected void multiply() {
        this.daysSinceMultiply = 0;
        Pair <Integer,Integer> newCoords = new Pair<>(0,0);

        for(int i = 0; i < 6; ++i){
            if(API.worldSPI.getField(API.worldSPI.getCoordsInDirection(Enums.Directions.values()[i],this.coordinates)) == null )
            {
                newCoords = API.worldSPI.getCoordsInDirection(Enums.Directions.values()[i],this.coordinates);
                break;
            }
        }

        Organism.create(this.specimen, newCoords);

        API.worldSPI.log(this, "creates new " + this.getSpecies() + " at " + newCoords.toString());
    }

    private void fight(Organism fighter)
    {
        if(fighter.getType() != PLANT || fighter.getSpecies() == FLOWER) {

            if (this.strength > fighter.getValue(STRENGTH)) {
                fighter.die(this);
                API.worldSPI.setField(this.coordinates, this);
            }
            else this.die(fighter);
        }

        else if(fighter.getSpecies() == HOGWEED)
        {
            if(this.strength <= fighter.getValue(STRENGTH)) this.die(fighter);
            else this.setCoords(this.oldCoords);
        }

        else this.setCoords(this.oldCoords);
    }

    private void eat(Organism eaten)
    {
        if(eaten.getType() == PLANT)
        {
            if(this.strength >= eaten.getValue(STRENGTH)) {
                eaten.die(this);
                API.worldSPI.setField(this.coordinates, this);
            }

            else if(this.specimen == CYBERSHEEP && eaten.getSpecies() == HOGWEED) {
                eaten.die(this);
                API.worldSPI.setField(this.coordinates, this);
            }

            else this.setCoords(this.oldCoords);
        }

        else this.setCoords(oldCoords);
    }
}
