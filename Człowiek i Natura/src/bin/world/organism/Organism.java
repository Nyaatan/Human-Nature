package bin.world.organism;

import bin.enums.OrganismType;
import bin.enums.Species;
import bin.enums.Values;
import bin.system.Pair;
import bin.system.dataLoader.DataLoaderAPI;
import bin.world.WorldAPI;
import bin.world.WorldSPI;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Organism {

    int worldID;
    Pair<Integer,Integer> sectorID;

    Organism(int worldID, Species specimen, Pair<Integer, Integer> coords, Pair<Integer,Integer> sectorID)
    {
        this.worldID = worldID;
        HashMap<String,ArrayList<String>> creationData = DataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.age = 0;
        this.specimen = specimen;
        this.type = OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
        this.sectorID = sectorID;
    } //sets up values of organism based on data from species file

    Organism(int worldID, Species specimen, Pair<Integer, Integer> coords)
    {
        this.worldID = worldID;
        HashMap<String,ArrayList<String>> creationData = DataLoaderAPI.getBlockConfig(specimen.toString(), "species");
        this.age = 0;
        this.specimen = specimen;
        this.type = OrganismType.valueOf(creationData.get("type").get(0).toUpperCase());
        this.strength = randFromCreationData(creationData, 0, 1, "strength");
        this.initiative = randFromCreationData(creationData, 0, 1, "initiative");
        this.coordinates = coords;
        this.sectorID = WorldAPI.getMap(worldID).getSectorByCoords(coords).getID();
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
        WorldSPI.cleanCorpse(this.worldID, this.sectorID);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n");
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
