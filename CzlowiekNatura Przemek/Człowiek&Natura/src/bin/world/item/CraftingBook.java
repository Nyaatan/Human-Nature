package bin.world.item;

import lib.API;
import lib.Enums;
import lib.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static lib.Enums.ItemType.RESOURCE;

public class CraftingBook implements Serializable {
    private HashMap<Enums.ItemName, ArrayList<Pair<Enums.ItemName, Integer>>> book;

    public CraftingBook() {
        this.book = new HashMap<>();
        for(Enums.ItemName name : Enums.ItemName.values())
        {
            HashMap<String, ArrayList<String>> configs = API.dataLoaderAPI.getBlockConfig(name.toString(), "items");
            if(Enums.ItemType.valueOf(configs.get("type").get(0).toUpperCase())!=RESOURCE) {
                ArrayList<String> config = API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("recipe");
                this.book.put(name, parseRecipe(config));
            }
        }
    }

    public ArrayList<Pair<Enums.ItemName,Integer>> getRecipe(Enums.ItemName item)
    {
        return book.get(item);
    }

    private ArrayList<Pair<Enums.ItemName,Integer>> parseRecipe(ArrayList<String> recipe)
    {
        ArrayList<Pair<Enums.ItemName,Integer>> result = new ArrayList<>();
        for(String dataPair : recipe)
        {
            String[] newDataPair = dataPair.substring(1, dataPair.length() - 1).split(";");
            result.add(new Pair<>(Enums.ItemName.valueOf(newDataPair[0].toUpperCase()), Integer.parseInt(newDataPair[1])));
        }
        return result;
    }

    public String toString()
    {
        return book.toString();
    }
}
