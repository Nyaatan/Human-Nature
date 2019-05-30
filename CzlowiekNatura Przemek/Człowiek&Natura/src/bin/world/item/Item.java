package bin.world.item;

import lib.API;
import lib.Enums.*;
import lib.Pair;

//TODO COMMENTS
public class Item {
    private ItemName name;
    private Pair<String,String> description;
    private ItemType type;
    private EquipmentType eqType;
    private Buff buff;
    private int strength;

    public Item(ItemName name)
    {
        this.name = name;
        try {
            this.description = API.dataLoaderAPI.getItemDescription(name);
        } catch (Exception e) {
            this.description = new Pair<>(name.toString(), "No description right now");
        }
        this.type = ItemType.valueOf(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("type").get(0).toUpperCase());
        if(this.type.equals(ItemType.EQUIPMENT)) {
            this.eqType = EquipmentType.valueOf(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("eq_type").get(0).toUpperCase());
            this.buff = Buff.valueOf(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("buff").get(0).toUpperCase());
            try{
                this.strength = Integer.parseInt(API.dataLoaderAPI.getBlockConfig(name.toString(), "items").get("strength").get(0));
            } catch (Exception e) {
                this.strength = 0;
            }
        }
    }

    public ItemName getName() {return name;}
    public Pair<String, String> getDescription() {return description;}
    public ItemType getType() {return type;}

    public EquipmentType getEqType() { return this.eqType; }

    public Buff getBuff() { return this.buff; }

    public String toString()
    {
        return this.name.toString();
    }

    public int getStrength() {
        return strength;
    }
}
