package bin.world.organism.Human;

import bin.system.API;
import bin.system.Commander;
import bin.world.item.CraftingBook;
import bin.world.item.Item;
import bin.world.organism.Organism;
import lib.CommandRefusedException;
import lib.Enums;
import lib.Enums.Buff;
import lib.Pair;

import java.util.ArrayList;

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

    public void takeCommand(Commander commander) {
        this.age++;
        Enum command = commander.hearCommand();
        if(command.getClass() == Enums.Commands.Move.class) this.execCommand((Enums.Commands.Move) command);
        else if(command.getClass() == Enums.Commands.Use.class) this.execCommand((Enums.Commands.Use) command);
        else if(command.getClass() == Enums.Commands.Craft.class) this.execCommand((Enums.Commands.Craft) command);
    }

    private void execCommand(Enums.Commands.Move direction){
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
    public void move() {
        this.oldCoords = this.coordinates;
        this.setCoords(API.worldSPI.getCoordsInDirection(this.movementDirection, this.coordinates));
        if(API.worldSPI.getField(this.coordinates)!=null) this.interact(API.worldSPI.getField(this.coordinates));
    } //todo

    @Override
    public void interact(Organism interacted) {

    }

    @Override
    public void multiply() {

    }

    @Override
    public void die() {

    }
}
