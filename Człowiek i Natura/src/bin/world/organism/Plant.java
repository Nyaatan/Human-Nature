package bin.world.organism;
//TODO lepsze importy, Enums -> Enums.Species.AllSpecies itd.

import bin.system.API;
import lib.Enums;
import lib.Pair;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;

public class Plant extends Mob {
    private int lifeExpectancy;

    public Plant(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID) {
        super(worldID, specimen, coords, ID);
        this.lifeExpectancy = Integer.parseInt(API.dataLoaderAPI.getBlockConfig(specimen.toString().toUpperCase(),
                "species").get("life_expectancy").get(0));
    }

    public Plant(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(worldID, specimen, coords);
        this.lifeExpectancy = Integer.parseInt(API.dataLoaderAPI.getBlockConfig(specimen.toString().toUpperCase(),
                "species").get("life_expectancy").get(0));
    }

    @Override
    public void multiply() //returns ArrayList of new organisms
    {

        int range = abs(4-this.initiative); //range of multiplying

        for(int i=0;i<initiative;++i) //initiative tells how many times a plant tries to multiply in a single turn
        {
            Pair<Integer,Integer> coords = null;
            for(int j=0;j< ThreadLocalRandom.current().nextInt(1,range);++j) //get random field in range
            {
                coords = API.worldSPI.getCoordsInDirection(this.worldID,
                        Enums.Directions.values()[ThreadLocalRandom.current().nextInt(Enums.Directions.values().length)],
                        this.coordinates, this.sectorID);
            }
            if(ThreadLocalRandom.current().nextInt(100) < 4*this.initiative)  //take chance to multiply; chance is 4*initiative/100
            {
                API.worldSPI.makeOrganism(this.worldID, this.specimen, coords);
            }
        }
    }

    @Override //TODO
    public void move()
    {
        this.age++;
        if(this.age>this.lifeExpectancy/20) this.multiply();
        if(this.age<lifeExpectancy)
        {
            double advancement = Math.ceil((double)lifeExpectancy/7);
            if(ThreadLocalRandom.current().nextDouble(100) < advancement/(135*9*(advancement+8)))
            {
                this.die();
            }
        }
        else if(ThreadLocalRandom.current().nextInt(7)==2) this.die();
    }

    @Override //TODO
    public void interact(Organism interacted) {

    }
}
