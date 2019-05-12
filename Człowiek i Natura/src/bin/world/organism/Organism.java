package bin.world.organism;

import bin.enums.OrganismType;
import bin.enums.Species;
import bin.enums.Values;
import bin.system.DataLoader;
import bin.system.Pair;
import bin.world.WorldSPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Organism {

    int worldID;

    Organism(int worldID, Species specimen, Pair<Integer, Integer> coords)
    {
        this.worldID = worldID;
        HashMap<String,ArrayList<String>> creationData = DataLoader.getBlockConfig(specimen.toString(), "species");
        this.age = 0;
        this.specimen = specimen;
        this.type = OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
    } //sets up values of organism based on data from species file

    private int randFromCreationData(HashMap<String, ArrayList<String>> creationData, int idMin, int idMax, String key)
    {
        return ThreadLocalRandom.current().nextInt(Integer.parseInt(creationData.get(key).get(idMin)), Integer.parseInt(creationData.get(key).get(idMax))+1);
    } //rand a number in bounds from creation config

    int strength; //defines winner in case of collision between organisms
    int initiative; //defines, which organism will move first
    private int age; //turns since creation of an object
    OrganismType type; //type of organism, affects interactions
    Species specimen; //defines specimen of the organism, affecting stats and special interactions

    Pair <Integer, Integer> coordinates; //coordinates on world map

    public void setCoords(Pair<Integer, Integer> coords){ this.coordinates = coords; } //imports new coordinates on map

    public int getValue(Values value)
    {
        if (value.equals(Values.STRENGTH)) return this.strength;
        else if (value.equals(Values.INITIATIVE)) return this.initiative;
        else if (value.equals(Values.AGE)) return this.age;
        else return -1;
    } //returns one of values: STRENGTH, INITIATIVE, AGE

    public Species getSpecies() {return this.specimen;} //returns specimen of the organism

    public OrganismType getType() {return this.type;} //returns type of the organism

    public Pair<Integer,Integer> getCoords() {return this.coordinates;} //returns current coordinates of the organism

    public void die() //move organism to graveyard
    {
        WorldSPI.log(this.worldID ,this," dies");
        WorldSPI.setField(this.worldID, this.coordinates, null);
        this.setCoords(WorldSPI.graveyard);
        WorldSPI.setField(this.worldID, WorldSPI.graveyard, this);
        WorldSPI.cleanCorpse(this.worldID);
    }


}
