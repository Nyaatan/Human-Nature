package bin.world.organism;

import bin.system.API;
import bin.world.organism.Human.Human;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static lib.Enums.Species.Animals.HUMAN;

public abstract class Organism {

    protected int worldID;
    protected Pair<Integer,Integer> sectorID;
    protected Pair<Integer, Integer> oldCoords;

    public Organism(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords, Pair<Integer,Integer> sectorID)
    {
        this.worldID = worldID;
        HashMap<String,ArrayList<String>> creationData = API.dataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.age = 0;
        this.specimen = specimen;
        this.type = Enums.OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
        this.sectorID = sectorID;
    } //sets up values of organism based on data from species file

    public Organism(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        this.worldID = worldID;
        HashMap<String,ArrayList<String>> creationData = API.dataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.age = 0;
        this.specimen = specimen;
        this.type = Enums.OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
        this.sectorID = API.worldAPI.getMap(worldID).getSectorByCoords(coords).getID();
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

    public void setCoords(Pair<Integer, Integer> coords){ this.coordinates = coords; } //imports new coordinates on map

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

    public static Organism create(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords,
                                  Pair<Integer, Integer> sectorID)
    {
        Organism newOrganism;

        if (specimen.equals(HUMAN)){
            return new Human(worldID,specimen,coords,sectorID);
        }

        boolean isAnimal = false;

        Enums.Species.Animals[] aniVal = Enums.Species.Animals.values(); //get animal species

        for(Enums.Species.Animals animal : aniVal){
            if(animal.toString().equals(specimen.toString())) { isAnimal = true; break; } //check if given specimen is an animal
        }

        if(isAnimal) newOrganism = new Animal(worldID, specimen, coords, sectorID);
        else newOrganism = new Plant(worldID, specimen, coords, sectorID); //if not, it a plant

        return newOrganism;
    }

    public static Organism create(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        return create(worldID, specimen, coords, API.worldAPI.getMap(worldID).getSectorByCoords(coords).getID());
    }

    public abstract void move();

    public abstract void interact(Organism interacted);

    public abstract void multiply();

    public abstract void die(); //move organism to graveyard

}
