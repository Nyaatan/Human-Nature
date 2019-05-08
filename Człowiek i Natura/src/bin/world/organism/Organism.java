package bin.world.organism;

import bin.enums.GameObjectName;
import bin.enums.OrganismType;
import bin.enums.Species;
import bin.enums.Values;
import bin.system.Pair;
import bin.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Organism {

    Organism(Species specimen, Pair<Integer, Integer> coords)
    {
        HashMap<String,ArrayList<String>> creationData = World.dataLoader.getSpeciesConfig(specimen.toString());
        this.age = 0;
        this.specimen = specimen;
        this.type = OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
        this.dropTable = parseDropTableData(creationData.get("drop"));
    } //sets up values of organism based on data from species file

    private ArrayList<Pair<GameObjectName,Double>> parseDropTableData(ArrayList<String> dropTableData)
    {
        ArrayList<Pair<GameObjectName,Double>> result = new ArrayList<>();
        for(String dataPair : dropTableData)
        {
            String[] newDataPair = dataPair.substring(1, dataPair.length() - 1).split(";");
            result.add(new Pair<>(GameObjectName.valueOf(newDataPair[0].toUpperCase()), Double.valueOf(newDataPair[1])));
        }
        return result;
    } //parses dropTable data from config to workable format

    private int randFromCreationData(HashMap<String, ArrayList<String>> creationData, int idMin, int idMax, String key)
    {
        return ThreadLocalRandom.current().nextInt(Integer.parseInt(creationData.get(key).get(idMin)), Integer.parseInt(creationData.get(key).get(idMax))+1);
    } //rand a number in bounds from creation config

    protected int strength; //defines winner in case of collision between organisms
    protected int initiative; //defines, which organism will move first
    protected int age; //turns since creation of an object
    protected OrganismType type; //type of organism, affects interactions
    protected Species specimen; //defines specimen of the organism, affecting stats and special interactions

    protected Pair <Integer, Integer> coordinates; //coordinates on world map

    protected ArrayList<Pair<GameObjectName,Double>> dropTable = new ArrayList<>(); //list of chances for a GameObject drop upon death by human

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
        World.Log( this," dies");
        World.setField(this.coordinates, null);
        this.setCoords(World.graveyard);
        World.setField(World.graveyard, this);
        World.cleanCorpse();
    }
}
