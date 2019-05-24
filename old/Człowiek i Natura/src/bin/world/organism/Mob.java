package bin.world.organism;

import bin.system.API;
import bin.world.item.Item;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

abstract class Mob extends Organism {
    Mob(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID) {
        super(worldID, specimen, coords, ID);
        HashMap<String,ArrayList<String>> creationData = API.dataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.dropTable = parseDropTableData(creationData.get("drop"));

    }
    private ArrayList<Pair<Enums.ItemName,Double>> dropTable; //list of chances for a Item drop upon death by human

    Mob(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(worldID, specimen, coords);
        HashMap<String,ArrayList<String>> creationData = API.dataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.dropTable = parseDropTableData(creationData.get("drop"));
    }

    private ArrayList<Pair<Enums.ItemName,Double>> parseDropTableData(ArrayList<String> dropTableData)
    {
        ArrayList<Pair<Enums.ItemName,Double>> result = new ArrayList<>();
        for(String dataPair : dropTableData)
        {
            String[] newDataPair = dataPair.substring(1, dataPair.length() - 1).split(";");
            result.add(new Pair<>(Enums.ItemName.valueOf(newDataPair[0].toUpperCase()), Double.valueOf(newDataPair[1])));
        }
        return result;
    } //parses dropTable data from config to workable format

    public ArrayList<Item> drop()
    {
        ArrayList<Item> drop = new ArrayList<>();
        for( Pair<Enums.ItemName, Double> possibleDrop : dropTable)
        {
            if(ThreadLocalRandom.current().nextDouble(possibleDrop.getY()) < possibleDrop.getY())
                drop.add(new Item(possibleDrop.getX()));
        }
        return drop;
    }

    @Override
    public void die()
    {
        API.worldSPI.getHuman(this.worldID).take(this.drop());
        API.worldSPI.log(this.worldID ,this," dies");
        API.worldSPI.setField(this.worldID, this.coordinates, null);
        this.setCoords(API.worldSPI.graveyard);
        API.worldSPI.setField(this.worldID, API.worldSPI.graveyard, this);
        API.worldSPI.cleanCorpse(this.worldID, this.sectorID);
    }
}
