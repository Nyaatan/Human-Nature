package bin.world.organism;

import lib.API;
import bin.world.organism.Human.Human;
import lib.CommandRefusedException;
import lib.Enums;
import lib.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static lib.Enums.Species.AllSpecies.HUMAN;

public abstract class Organism implements Serializable {

    protected Pair<Integer,Integer> sectorID;
    protected Pair<Integer, Integer> oldCoords;
    final int ID;

    public Organism(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer,Integer> sectorID)
    {
        HashMap<String,ArrayList<String>> creationData = API.dataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.age = 0;
        this.specimen = specimen;
        this.type = Enums.OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
        this.sectorID = sectorID;
        this.ID = this.hashCode();
    } //sets up values of organism based on data from species file

    public Organism(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        HashMap<String,ArrayList<String>> creationData = API.dataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.age = 0;
        this.specimen = specimen;
        this.type = Enums.OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
        this.sectorID = API.worldAPI.getMap().getChunkByCoords(coords).getID();
        this.ID = this.hashCode();
    } //sets up values of organism based on data from species file

    private int randFromCreationData(HashMap<String, ArrayList<String>> creationData, int idMin, int idMax, String key)
    {
        return ThreadLocalRandom.current().nextInt(Integer.parseInt(creationData.get(key).get(idMin)), Integer.parseInt(creationData.get(key).get(idMax))+1);
    } //rand a number in bounds from creation config

    protected int strength; //defines winner in case of collision between organisms
    protected int initiative; //defines, which organism will move first
    protected int age; //turns since creation of an object
    protected Enums.OrganismType type; //type of organism, affects interactions
    protected Enums.Species.AllSpecies specimen; //defines specimen of the organism, affecting stats and special interactions

    protected Pair<Integer, Integer> coordinates; //coordinates on world map

    public void setCoords(Pair<Integer, Integer> coords){
        this.coordinates = coords;
    } //imports new coordinates on map

    public int getValue(Enums.Values value)
    {
        if (value.equals(Enums.Values.STRENGTH)) return this.strength;
        else if (value.equals(Enums.Values.INITIATIVE)) return this.initiative;
        else if (value.equals(Enums.Values.AGE)) return this.age;
        else return -1;
    } //returns one of values: STRENGTH, INITIATIVE, AGE

    public Enums.Species.AllSpecies getSpecies() {return this.specimen;} //returns specimen of the organism

    public Enums.OrganismType getType() {return this.type;} //returns type of the organism

    public Pair<Integer,Integer> getCoords() {return this.coordinates;} //returns current coordinates of the organism

    public static Organism create(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords,
                                  Pair<Integer, Integer> sectorID)
    {
        Organism newOrganism;

        if (specimen.equals(HUMAN)){
            return new Human(specimen,coords,sectorID);
        }

        boolean isAnimal = false;

        Enums.Species.Animals[] aniVal = Enums.Species.Animals.values(); //get animal species

        for(Enums.Species.Animals animal : aniVal){
            if(animal.toString().equals(specimen.toString())) { isAnimal = true; break; } //check if given specimen is an animal
        }

        if(isAnimal) newOrganism = new Animal(specimen, coords, sectorID);
        else newOrganism = new Plant(specimen, coords, sectorID); //if not, it a plant

        return newOrganism;
    }

    public static Organism create(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        Organism newOrganism = create(specimen, coords, API.worldAPI.getMap().getChunkByCoords(coords).getID());
        return newOrganism;
    }

    public abstract void move() throws CommandRefusedException;

    protected abstract void interact(Organism interacted) throws CommandRefusedException;

    protected abstract void multiply();

    public abstract void die(Organism killer); //move organism to graveyard

    public String toString()
    {
        return this.getSpecies() + "(" + this.getCoords()+ ", ID: " + this.ID + ")";
    }

}
