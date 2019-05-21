package bin.world.organism;

import bin.system.Commander;
import bin.world.item.Item;
import lib.Enums;
import lib.Enums.EquipmentType;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Human extends Organism {
    public Human(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords,
                 Pair<Integer, Integer> sectorID) {
        super(worldID, specimen, coords, sectorID);
        this.inventory = new HashMap<>();
        this.equipment = new HashMap<>();
    }
    public Human(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(worldID, specimen, coords);
        this.inventory = new HashMap<>();
        this.equipment = new HashMap<>();
    }

    private HashMap<Item, Integer> inventory;
    private HashMap<EquipmentType, Item> equipment;

    public void takeCommand(Commander commander) {
        Enum command = commander.hearCommand();
        if(command.getClass() == Enums.Commands.Move.class) this.execCommand((Enums.Commands.Move) command);
        else if(command.getClass() == Enums.Commands.Use.class) this.execCommand((Enums.Commands.Use) command);
        else if(command.getClass() == Enums.Commands.Craft.class) this.execCommand((Enums.Commands.Craft) command);
    }

    public void execCommand(Enums.Commands.Move direction){}
    public void execCommand(Enums.Commands.Use item){}
    public void execCommand(Enums.Commands.Craft item){}

    private void removeFromInventory(Item item)
    {
        if(this.inventory.containsKey(item)){
            if(this.inventory.get(item) > 1) this.inventory.put(item, this.inventory.get(item)-1);
            else this.inventory.remove(item);
        }
    }

    private void addToInventory(Item item)
    {
        if(this.inventory.containsKey(item)) this.inventory.put(item, this.inventory.get(item)+1);
        else this.inventory.put(item, 1);
    }

    private void take(ArrayList<Item> loot)
    {
        if(loot!=null)
        {
            for(Item item : loot)
            {
                addToInventory(item);
            }
            loot.clear();
        }
    }



    @Override
    public void move() {
        this.age++;
    }

    @Override
    public void interact(Organism interacted) {

    }

    @Override
    public void multiply() {

    }
}
