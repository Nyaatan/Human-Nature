package bin.world.organism.Human;

import bin.world.item.Item;

import java.util.HashMap;

class Inventory {
    private HashMap<Item, Integer> inventory;

    Inventory()
    {
        this.inventory = new HashMap<>();
    }

    void remove(Item item)
    {
        if(this.inventory.containsKey(item)){
            if(this.inventory.get(item) > 1) this.inventory.put(item, this.inventory.get(item)-1);
            else this.inventory.remove(item);
        }
    }

    void add(Item item)
    {
        if(this.inventory.containsKey(item)) this.inventory.put(item, this.inventory.get(item)+1);
        else this.inventory.put(item, 1);
    }
}
