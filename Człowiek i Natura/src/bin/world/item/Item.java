package bin.world.item;

import bin.system.API;
import lib.Enums;
import lib.Pair;

//TODO COMMENTS
public class Item {
    private Enums.ItemName name;
    private Pair<String,String> description;
    private Enums.ItemType type;
    //TODO traits

    public Item(Enums.ItemName name)
    {
        this.name = name;
        this.description = API.dataLoaderAPI.getItemDescription(name);
        this.type = Enums.ItemType.valueOf(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("type").get(0));
    }

    public Enums.ItemName getName() {return name;}
    public Pair<String, String> getDescription() {return description;}
    public Enums.ItemType getType() {return type;}
}
