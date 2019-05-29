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

    Equipment(Inventory inventory)
    {
        this.equipment = new HashMap<>();
        this.inventory = inventory;
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
            this.equipment.remove(eqType);
        }
    }

    public Collection<Item> getAll() {
        return equipment.values();
    }
}
