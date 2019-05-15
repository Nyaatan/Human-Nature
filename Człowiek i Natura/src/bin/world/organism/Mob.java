package bin.world.organism;

import bin.enums.Species;
import bin.enums.item.ItemName;
import bin.system.dataLoader.DataLoaderAPI;
import bin.system.Pair;
import bin.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

abstract class Mob extends Organism {
    Mob(int worldID, Species specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID) {
        super(worldID, specimen, coords, ID);
        HashMap<String,ArrayList<String>> creationData = DataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.dropTable = parseDropTableData(creationData.get("drop"));

    }
    private ArrayList<Pair<ItemName,Double>> dropTable; //list of chances for a Item drop upon death by human

    Mob(int worldID, Species specimen, Pair<Integer, Integer> coords) {
        super(worldID, specimen, coords);
    }

    private ArrayList<Pair<ItemName,Double>> parseDropTableData(ArrayList<String> dropTableData)
    {
        ArrayList<Pair<ItemName,Double>> result = new ArrayList<>();
        for(String dataPair : dropTableData)
        {
            String[] newDataPair = dataPair.substring(1, dataPair.length() - 1).split(";");
            result.add(new Pair<>(ItemName.valueOf(newDataPair[0].toUpperCase()), Double.valueOf(newDataPair[1])));
        }
        return result;
    } //parses dropTable data from config to workable format

    public ArrayList<Item> drop()
    {
        ArrayList<Item> drop = new ArrayList<>();
        for( Pair<ItemName, Double> possibleDrop : dropTable)
        {
            if(ThreadLocalRandom.current().nextDouble(possibleDrop.getY()) < possibleDrop.getY())
                drop.add(new Item(possibleDrop.getX()));
        }
        return drop;
    }

}
