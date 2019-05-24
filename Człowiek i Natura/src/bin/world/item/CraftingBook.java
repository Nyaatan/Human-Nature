package bin.world.item;

import bin.system.API;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingBook {
    private HashMap<Enums.ItemName, ArrayList<Pair<Enums.ItemName, Integer>>> book;

    public CraftingBook() {
        this.book = new HashMap<>();
        for(Enums.ItemName name : Enums.ItemName.values())
        {
            HashMap<String, ArrayList<String>> config = API.dataLoaderAPI.getBlockConfig(name.toString(), "craftingBook");
            ArrayList<Pair<Enums.ItemName, Integer>> recipe = new ArrayList<>();
            for(String key : config.keySet())
            {
                recipe.add(new Pair<>(Enums.ItemName.valueOf(key.toUpperCase()), Integer.parseInt(config.get(key).get(0))));
            }
            this.book.put(name, recipe);
        }
    }

    public ArrayList<Pair<Enums.ItemName,Integer>> getRecipe(Enums.ItemName item)
    {
        return book.get(item);
    }
}
