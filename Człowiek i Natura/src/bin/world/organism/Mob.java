package bin.world.organism;

import lib.API;
import bin.world.item.Item;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

abstract class Mob extends Organism {

    protected int lifeExpectancy;
    int daysSinceMultiply = 0;
    Mob(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID) {
        super(specimen, coords, ID);
        getData();
    }
    private ArrayList<Pair<Enums.ItemName,Integer>> dropTable; //list of chances for a Item drop upon death by human

    Mob(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);
        getData();
    }

    private void getData()
    {
        HashMap<String,ArrayList<String>> creationData = API.dataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.dropTable = parseDropTableData(creationData.get("drop"));
        this.lifeExpectancy = Integer.parseInt(API.dataLoaderAPI.getBlockConfig(specimen.toString().toUpperCase(),
                "species").get("life_expectancy").get(0)); //get life expectancy
    }

    private ArrayList<Pair<Enums.ItemName, Integer>> parseDropTableData(ArrayList<String> dropTableData)
    {
        ArrayList<Pair<Enums.ItemName,Integer>> result = new ArrayList<>();
        for(String dataPair : dropTableData)
        {
            String[] newDataPair = dataPair.substring(1, dataPair.length() - 1).split(";");
            result.add(new Pair<>(Enums.ItemName.valueOf(newDataPair[0].toUpperCase()), Integer.parseInt(newDataPair[1])));
        }
        return result;
    } //parses dropTable data from config to workable format

    public ArrayList<Item> drop()
    {
        ArrayList<Item> drop = new ArrayList<>();
        for( Pair<Enums.ItemName, Integer> possibleDrop : dropTable)
        {
            if(ThreadLocalRandom.current().nextInt(100) < possibleDrop.getY())
                drop.add(new Item(possibleDrop.getX()));
        }
        return drop;
    }

    @Override
    public void die(Organism killer)
    {
        if(killer.getSpecies()== Enums.Species.AllSpecies.HUMAN)
            API.worldSPI.getHuman().take(this.drop());
        API.worldSPI.log(this," dies by ", killer);
        API.worldSPI.setField(this.coordinates, null);
        API.worldSPI.cleanCorpse(this);
    }

    protected void departThisWorld()
    {
        if(this.age<lifeExpectancy) //if too young to die easily, try dying with a formula: ageAdvancement * 1/(135*9*(ageAdvancement+8)
        {
            double advancement = Math.ceil((double)lifeExpectancy/7); //one of 7 stages of age advancement
            if(ThreadLocalRandom.current().nextDouble(1) < advancement/(135*9*(advancement+8)))
            {
                this.die(this);
            }
        }
        else if(ThreadLocalRandom.current().nextInt(7)==2)
        {
            this.die(this);
        }
    }
}
