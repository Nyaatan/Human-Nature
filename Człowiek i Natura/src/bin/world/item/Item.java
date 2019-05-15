package bin.world.item;

import bin.enums.item.ItemName;
import bin.enums.item.ItemType;
import bin.system.Pair;
import bin.system.dataLoader.DataLoaderAPI;

//TODO COMMENTS
public class Item {
    private ItemName name;
    private Pair<String,String> description;
    private ItemType type;

    public Item(ItemName name)
    {
        this.name = name;
        this.description = DataLoaderAPI.getItemDescription(name);
        this.type = ItemType.valueOf(DataLoaderAPI.getBlockConfig(name.toString(), "items").get("type").get(0));
    }

    public ItemName getName() {return name;}
    public Pair<String, String> getDescription() {return description;}
    public ItemType getType() {return type;}
}
