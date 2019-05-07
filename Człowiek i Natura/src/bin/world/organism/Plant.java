package bin.world.organism;

import bin.enums.Directions;
import bin.enums.Species;
import bin.interfaces.InteractionsPassive;
import bin.system.Pair;
import bin.world.World;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class Plant extends Organism implements InteractionsPassive {
    public Plant(Species specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);
    }

    @Override
    public void Multiply() //returns ArrayList of new organisms
    {

        int range = abs(4-this.initiative); //range of multiplying

        for(int i=0;i<initiative;++i) //initiative tells how many times a plant tries to multiply in a single turn
        {
            Pair<Integer,Integer> coords = null;
            for(int j=0;j< ThreadLocalRandom.current().nextInt(1,range);++j) //get random field in range
            {
                coords = World.getCoordsInDirection(Directions.values()[ThreadLocalRandom.current().nextInt(Directions.values().length)], this.coordinates);
            }
            if(ThreadLocalRandom.current().nextInt(100) < 4*this.initiative)  //take chance to multiply; chance is 4*initiative/100
            {
                World.makeOrganism(this.specimen, coords);
            }
        }
    }
}
