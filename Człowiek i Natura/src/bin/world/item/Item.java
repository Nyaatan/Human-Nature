package bin.world.item;

import bin.enums.item.ItemName;
import bin.enums.item.ItemType;
import bin.system.DataLoader;
import bin.system.Pair;

//TODO COMMENTS
public class Item {
    private ItemName name;
    private Pair<String,String> description;
    private ItemType type;

    public Item(ItemName name)
    {
        this.name = name;
        this.description = DataLoader.getItemDescription(name);
        this.type = ItemType.valueOf(DataLoader.getBlockConfig(name.toString(), "items").get("type").get(0));
    }

    public ItemName getName() {return name;}
    public Pair<String, String> getDescription() {return description;}
    public ItemType getType() {return type;}
}
