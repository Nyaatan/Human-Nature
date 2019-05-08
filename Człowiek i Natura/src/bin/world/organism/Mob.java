package bin.world.organism;

import bin.enums.GameObjectName;
import bin.enums.Species;
import bin.system.DataLoader;
import bin.system.Pair;
import bin.world.gameObject.GameObject;

import java.util.ArrayList;
import java.util.HashMap;

abstract class Mob extends Organism {
    Mob(Species specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);
        HashMap<String,ArrayList<String>> creationData = DataLoader.getSpeciesConfig(specimen.toString());
        this.dropTable = parseDropTableData(creationData.get("drop"));

    }
    private ArrayList<Pair<GameObjectName,Double>> dropTable; //list of chances for a GameObject drop upon death by human

    private ArrayList<Pair<GameObjectName,Double>> parseDropTableData(ArrayList<String> dropTableData)
    {
        ArrayList<Pair<GameObjectName,Double>> result = new ArrayList<>();
        for(String dataPair : dropTableData)
        {
            String[] newDataPair = dataPair.substring(1, dataPair.length() - 1).split(";");
            result.add(new Pair<>(GameObjectName.valueOf(newDataPair[0].toUpperCase()), Double.valueOf(newDataPair[1])));
        }
        return result;
    } //parses dropTable data from config to workable format

    public GameObject drop()
    {
        GameObject possibleDrop = new GameObject(null,null,null); //TODO
        return possibleDrop;
    }

}
