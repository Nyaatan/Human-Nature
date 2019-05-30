package bin.world.organism.Human;

import bin.world.item.Item;
import lib.Enums;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import static lib.Enums.ItemType.EQUIPMENT;

public class Equipment implements Serializable {
    private HashMap<Enums.EquipmentType, Item> equipment;
    private Inventory inventory;
    private Buffs buffs;

    Equipment(Inventory inventory, Buffs buffs)
    {
        this.equipment = new HashMap<>();
        this.inventory = inventory;
        this.buffs = buffs;
    }

    void equip(Item item)
    {
        if(item.getType().equals(EQUIPMENT))
        {
            if (equipment.containsKey(item.getEqType())){
                this.unequip(item.getEqType());
                this.equipment.put(item.getEqType(), item);
            }
            else this.equipment.put(item.getEqType(), item);

            inventory.remove(item);
        }
    }

    private void unequip(Enums.EquipmentType eqType)
    {
        if(this.equipment.containsKey(eqType)) {
            this.inventory.add(this.equipment.get(eqType));
            this.buffs.remove(this.equipment.get(eqType).getBuff());
            this.equipment.remove(eqType);
        }
    }

    public Collection<Item> getAll() {
        return equipment.values();
    }
}
