package bin.world.organism.Human;

import bin.world.item.Item;
import lib.Enums.ItemName;

import java.io.Serializable;
import java.util.HashMap;

class Inventory implements Serializable {
    private HashMap<ItemName, Integer> inventory;

    Inventory()
    {
        this.inventory = new HashMap<>();
    }

    void remove(Item item)
    {
        if(this.inventory.containsKey(item.getName())){
            if(this.inventory.get(item.getName()) > 1) this.inventory.put(item.getName(), this.inventory.get(item.getName())-1);
            else this.inventory.remove(item.getName());
        }
    }

    void add(Item item)
    {
        if(this.inventory.containsKey(item.getName())) this.inventory.put(item.getName(), this.inventory.get(item.getName())+1);
        else this.inventory.put(item.getName(), 1);
    }

    public String toString()
    {
        return this.inventory.toString();
    }
}
