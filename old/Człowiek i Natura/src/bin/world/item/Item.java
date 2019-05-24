package bin.world.item;

import bin.system.API;
import lib.Enums.*;
import lib.Pair;

//TODO COMMENTS
public class Item {
    private ItemName name;
    private Pair<String,String> description;
    private ItemType type;
    private EquipmentType eqType;
    private Buff buff;
    //TODO traits

    public Item(ItemName name)
    {
        this.name = name;
        this.description = API.dataLoaderAPI.getItemDescription(name);
        this.type = ItemType.valueOf(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("type").get(0));
        if(this.type.equals(ItemType.EQUIPMENT))
            this.eqType = EquipmentType.valueOf(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("eq_type").get(0));
        this.buff = Buff.valueOf(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("buff").get(0));
    }

    public ItemName getName() {return name;}
    public Pair<String, String> getDescription() {return description;}
    public ItemType getType() {return type;}

    public EquipmentType getEqType() { return this.eqType; }

    public Buff getBuff() { return this.buff; }
}
