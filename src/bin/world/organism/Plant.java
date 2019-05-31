package bin.world.organism;
//TODO lepsze importy, Enums -> Enums.Species.AllSpecies itd.

import bin.world.item.Item;
import lib.API;
import lib.Enums;
import lib.Pair;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;
import static lib.Enums.Species.AllSpecies.OAK;

public class Plant extends Mob {

    public Plant(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID) {
        super(specimen, coords, ID);

    }

    public Plant(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);

    }

    @Override
    protected void multiply() //returns ArrayList of new organisms
    {

        int range = abs(4-this.initiative)+1; //range of multiplying

        for(int i=0;i<initiative;++i) //initiative tells how many times a plant tries to multiply in a single turn
        {
            Pair<Integer,Integer> coords = null;
            for(int j=0;j< ThreadLocalRandom.current().nextInt(1,range);++j) //get random field in range
            {
                coords = API.worldSPI.getCoordsInDirection(
                        Enums.Directions.values()[ThreadLocalRandom.current().nextInt(Enums.Directions.values().length)],
                        this.coordinates);
            }
            if(ThreadLocalRandom.current().nextInt(100) < 4*this.initiative)  //take chance to multiply; chance is 4*initiative/100
            {
                if(API.worldSPI.getField(coords)!=null) interact(API.worldSPI.getField(coords));
                if(API.worldSPI.getField(coords)==null)API.worldSPI.makeOrganism(this.specimen, coords);
                API.worldSPI.log(this, "creates new "+ this.getSpecies() + " at " + coords);
            }
        }
    }

    @Override //TODO TEST
    public void move() //increase age, try multiplying and dying
    {
        this.age++;
        this.daysSinceMultiply++;
        if(this.age>this.lifeExpectancy/20 && daysSinceMultiply>abs(6-initiative)) this.multiply();
        departThisWorld();
    }

    @Override //TODO
    public void interact(Organism interacted) {
        switch (this.specimen) {
            case HOGWEED:
                if (interacted.getType() == Enums.OrganismType.PLANT && interacted.getSpecies() != OAK)
                    interacted.die(this);
                break;

            case OAK:
                switch (interacted.getSpecies())
                {
                    case HUMAN:
                        API.worldSPI.getHuman().take(new Item(Enums.ItemName.BRANCH));
                        interacted.setCoords(interacted.oldCoords);
                        break;
                }
        }
    }
}
