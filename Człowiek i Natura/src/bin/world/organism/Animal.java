package bin.world.organism;

import bin.enums.Directions;
import bin.enums.Species;
import bin.interfaces.InteractionsActive;
import bin.interfaces.InteractionsPassive;
import bin.system.Pair;
import bin.world.World;

import java.util.concurrent.ThreadLocalRandom;

import static bin.enums.OrganismType.*;
import static bin.enums.Species.*;
import static bin.enums.Values.*;
//TODO COMMENTS
public class Animal extends Organism implements InteractionsPassive, InteractionsActive {

    private Pair<Integer, Integer> oldCoords;

    public Animal(Species specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);
    }

    @Override
    public void Interact(Organism interacted) {
        if(this.specimen == interacted.getSpecies()) {this.Multiply(); this.setCoords(this.oldCoords);}
        else if(this.type == PREDATOR)
        {
            if(interacted.getType() != PLANT || interacted.getSpecies() == FLOWER) {

                if (this.strength > interacted.getValue(STRENGTH)) { interacted.die(); World.setField(this.coordinates, this); }
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
                if(this.strength >= interacted.getValue(STRENGTH)) { interacted.die(); World.setField(this.coordinates, this); }
                else if(this.specimen == CYBERSHEEP && interacted.getSpecies() == HOGWEED) { interacted.die(); World.setField(this.coordinates, this);}
                else this.setCoords(this.oldCoords);
            }
            else this.setCoords(oldCoords);
        }
    }

    @Override
    public void Move() {
        this.oldCoords = this.coordinates;
        this.setCoords(World.getCoordsInDirection(Directions.values()[ThreadLocalRandom.current().nextInt(Directions.values().length)],
                this.coordinates)); //get coordinates in random direction from enum Directions, then move
        if(World.getField(this.coordinates) != null) {
            this.Interact(World.getField(this.coordinates));
        }
        else { World.setField(this.oldCoords, null); World.setField(this.coordinates, this); }
    }

    @Override
    public void Multiply() {

        Pair <Integer,Integer> newCoords = new Pair<>(0,0);

        for(int i = 0; i < 6; ++i){
            if(World.getField(World.getCoordsInDirection(Directions.values()[i],this.coordinates)) == null ){
                newCoords = World.getCoordsInDirection(Directions.values()[i],this.coordinates); break;}
        }

        World.makeOrganism(this.specimen, newCoords);
    }
}
