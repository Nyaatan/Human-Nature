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
    public Human(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords,
                 Pair<Integer, Integer> sectorID) {
        super(specimen, coords, sectorID);
        allocateMemory();
    }
    public Human(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) {
        super(specimen, coords);
        allocateMemory();
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
        this.movementDirection = Enums.Directions.valueOf(direction.toString());
        this.move();
    }
    private void execCommand(Enums.Commands.Use item){}
    private void execCommand(Enums.Commands.Craft item){}

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

    private void addBuff(Buff buff)
    {
        this.buffs.remove(buff);
        this.buffs.add(buff);
    }

    private void use(Item item) throws CommandRefusedException {
        if(item.getType().equals(EQUIPMENT)) this.equipment.equip(item);
        else if(item.getType().equals(BUFF))
        {
            this.addBuff(item.getBuff());
            this.inventory.remove(item);
        }
        else if(item.getType().equals(CRAFTING)) this.refuseCommand();
    }

    private void refuseCommand() throws CommandRefusedException {
        throw new CommandRefusedException();
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
        if(fighter.getType()!= Enums.OrganismType.PLANT || fighter.getSpecies().equals(Enums.Species.AllSpecies.HOGWEED)) {
            if (this.strength > fighter.getValue(Enums.Values.STRENGTH)) fighter.die(this);
            else this.die(fighter);
        }
        else
        {
            if (this.strength > fighter.getValue(Enums.Values.STRENGTH)) fighter.die(this);
            else
            {
                this.setCoords(this.oldCoords);
                this.refuseCommand();
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
        System.out.println("AAAAAAAAAAAAAGHGHGHGHGHGh");
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
}
