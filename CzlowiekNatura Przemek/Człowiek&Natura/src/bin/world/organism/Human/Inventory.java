package bin.world.organism.Human;

import bin.world.item.Item;
import lib.Enums.ItemName;

import java.io.Serializable;
import java.util.HashMap;

public class Inventory implements Serializable {
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

    public boolean contains(ItemName itemName) {
        return inventory.containsKey(itemName);
    }

    public Item get(ItemName itemName)
    {
        return new Item(itemName);
    }

    public int count(ItemName itemName) {
        if(contains(itemName))
            return inventory.get(itemName);
        else return 0;
    }

    public void remove(ItemName name, Integer count) {
        for(int i=0;i<4;++i) remove(name);
    }

    private void remove(ItemName name) {
        remove(new Item(name));
    }

    public boolean contains(ItemName item, int count) {
        return count(item) > 0;
    }

    public HashMap<ItemName, Integer> getInventory() { return inventory; }
}
