package bin.world.organism.Human;

import bin.system.Commander;
import bin.world.item.CraftingBook;
import bin.world.item.Item;
import bin.world.organism.Organism;
import lib.API;
import lib.CommandRefusedException;
import lib.Enums;
import lib.Enums.Buff;
import lib.Pair;

import java.util.ArrayList;

import static lib.Enums.Buff.HOGWEED_RESISTANT;
import static lib.Enums.Buff.TIMBERMAN;
import static lib.Enums.ItemType.*;

public class Human extends Organism {
    private final int baseStr;

    public boolean alive = true;

    public Human(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords,
                 Pair<Integer, Integer> sectorID) {
        super(specimen, coords, sectorID);
        allocateMemory();
        baseStr = strength;
    }
    public Human(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);
        allocateMemory();
        baseStr = strength;
    }

    private void allocateMemory()
    {
        this.inventory = new Inventory();
        this.equipment = new Equipment(this.inventory);
        this.buffs = new Buffs();
        this.craftingBook = new CraftingBook();
    }

    private CraftingBook craftingBook;
    private Inventory inventory;
    private Equipment equipment;
    private Buffs buffs;

    private Enums.Directions movementDirection;

    public void takeCommand(Commander commander) throws CommandRefusedException {
        Enum command = commander.hearCommand();
        if(command.getClass() == Enums.Commands.Move.class) this.execCommand((Enums.Commands.Move) command);
        else if(command.getClass() == Enums.Commands.Use.class) this.execCommand((Enums.Commands.Use) command);
        else if(command.getClass() == Enums.Commands.Craft.class) this.execCommand((Enums.Commands.Craft) command);
        this.age++;
    }

    private void execCommand(Enums.Commands.Move direction) throws CommandRefusedException {
        if(direction!= Enums.Commands.Move.WAIT) {
            this.movementDirection = Enums.Directions.valueOf(direction.toString());
            this.move();
        }
    }
    private void execCommand(Enums.Commands.Use item) throws CommandRefusedException {this.use(Enums.ItemName.valueOf(item.toString()));}
    private void execCommand(Enums.Commands.Craft item){ this.craft(Enums.ItemName.valueOf(item.toString()));}

    private void craft(Enums.ItemName item) {
        ArrayList<Pair<Enums.ItemName, Integer>> recipe = craftingBook.getRecipe(item);

        if(canCraft(item))
        {
            for(Pair<Enums.ItemName, Integer> itemCount : recipe)
            {
                inventory.remove(itemCount.getX(), itemCount.getY());
            }
            inventory.add(new Item(item));
        }
    }


    public void take(ArrayList<Item> loot)
    {
        if(loot!=null)
        {
            for(Item item : loot)
            {
                this.inventory.add(item);
            }
            loot.clear();
        }
    }

    public void take(Item item) {
        if(item!=null) this.inventory.add(item);
    }
    private void addBuff(Buff buff)
    {
        this.buffs.remove(buff);
        this.buffs.add(buff);
    }

    private void use(Item item) throws CommandRefusedException {
        if(item.getType().equals(EQUIPMENT)) {
            this.equipment.equip(item);
            this.addBuff(item.getBuff());
            calculateStats();
        }

        else if(item.getType().equals(BUFF))
        {
            this.addBuff(item.getBuff());
            this.inventory.remove(item);
        }
        else if(item.getType().equals(CRAFTING)) this.refuseCommand("Not a usable item");
    }

    private void use(Enums.ItemName itemName) throws CommandRefusedException {
        if(inventory.contains(itemName))
        {
            use(inventory.get(itemName));
        }
        else refuseCommand("No such item");
    }

    private void calculateStats() {
        strength = baseStr;
        for(Item item : equipment.getAll())
        {
            strength+=item.getStrength();
        }
        for(Buff buff : buffs.getAll())
        {
            strength+=buff.strength;
        }
    }

    private void refuseCommand(String message) throws CommandRefusedException {
        this.coordinates = this.oldCoords;
        throw new CommandRefusedException(message);
    }

    @Override
    public void move() throws CommandRefusedException {
        this.oldCoords = this.coordinates;
        this.setCoords(API.worldSPI.getCoordsInDirection(this.movementDirection, this.coordinates));
        if(API.worldSPI.getField(this.coordinates)!=null) this.interact(API.worldSPI.getField(this.coordinates));
        API.worldSPI.setField(this.oldCoords, null);
        API.worldSPI.setField(this.coordinates, this);
        if(API.worldAPI.getMap().getChunkByCoords(this.coordinates)!=API.worldAPI.getMap().getChunkByCoords(this.oldCoords))
        {
            API.worldAPI.getMap().changeCenter(API.worldAPI.getMap().getChunkByCoords(this.coordinates).getID());
        }
    }

    private void fight(Organism fighter, boolean buffed) throws CommandRefusedException {
        if(buffed) this.strength += 15;
        if(fighter.getType()!= Enums.OrganismType.PLANT || fighter.getSpecies()== Enums.Species.AllSpecies.HOGWEED) {
            if (this.strength > fighter.getValue(Enums.Values.STRENGTH)) fighter.die(this);
            else this.die(fighter);
        }
        else
        {
            if (this.strength > fighter.getValue(Enums.Values.STRENGTH)) fighter.die(this);
            else
            {
                fighter.interact(this);
            }
        }
        if(buffed) this.strength -= 15;
    }

    @Override
    public void interact(Organism interacted) throws CommandRefusedException {
        this.age++;
        switch (interacted.getSpecies()) {
            case OAK:
                fight(interacted, this.buffs.contains(TIMBERMAN));
                break;
            case HOGWEED:
                fight(interacted, this.buffs.contains(HOGWEED_RESISTANT));
                break;
            default:
                fight(interacted, false);
        }
    }

    @Override
    public void multiply() {

    }

    @Override
    public void die(Organism killer) {
        alive = false;
    }

    public CraftingBook getCraftingBook() {
        return craftingBook;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public Equipment getEquipment() { return equipment; }

    public Buffs getBuffs() {
        return buffs;
    }

    public boolean canCraft(Enums.Commands.Craft item) {
        for(Pair<Enums.ItemName, Integer> itemPair : craftingBook.getRecipe(Enums.ItemName.valueOf(item.toString())))
        {
            if(!inventory.contains(itemPair.getX(), itemPair.getY()))
            {
                return false;
            }
        }
        return true;
    }

    private boolean canCraft(Enums.ItemName item) {
        return canCraft(Enums.Commands.Craft.valueOf(item.toString()));
    }
}
