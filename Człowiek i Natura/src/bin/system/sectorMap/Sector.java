package bin.system.sectorMap;

import bin.enums.Values;
import bin.system.GlobalSettings;
import bin.system.Pair;
import bin.system.dataLoader.DataLoaderAPI;
import bin.world.organism.Organism;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Sector {
    private Organism[][] sector;
    private Pair<Integer,Integer> size;
    private Pair<Integer,Integer> ID;
    private ArrayList<ArrayList<Organism>> organisms; //TODO japierdole jestem debilem <--------

    public Sector(Pair <Integer,Integer> size, Pair<Integer,Integer> ID)
    {
        this.organisms = new ArrayList<>();
        this.ID = ID;
        this.size = size;
        this.sector = new Organism[size.getX()][size.getY()];
        for(int i = 0; i< GlobalSettings.MAX_INITIATIVE; ++i)
        {
            this.organisms.add(new ArrayList<>());
        }
    }

    public Sector(int x, int y, Pair<Integer,Integer> ID) {
        this.organisms = new ArrayList<>();
        for(int i = 0; i< GlobalSettings.MAX_INITIATIVE; ++i)
        {
            this.organisms.add(new ArrayList<>());
        }
        this.ID = ID;
        this.size = new Pair<>(x,y);
        this.sector = new Organism[x][y];
    }

    public Pair<Integer,Integer> toLocalCoords(Pair<Integer,Integer> globalCoords)
    {
        return new Pair<>(Math.floorMod(globalCoords.getX(),size.getX()), Math.floorMod(globalCoords.getY(), size.getY()));
    }

    public Pair<Integer,Integer> toGlobalCoords(Pair<Integer,Integer> localCoords)
    {
        Pair<Integer,Integer> defaultSectorSize = Pair.makeIntPair(DataLoaderAPI.getConfig("sector_size", "config"));
        return new Pair<>(this.ID.getX()*defaultSectorSize.getX()+localCoords.getX(), this.ID.getY()*defaultSectorSize.getY());
    }

    public Organism getFieldLocal(Pair<Integer,Integer> localCoords)
    {
        return this.sector[(localCoords.getX())][localCoords.getY()];
    }

    public void setFieldLocal(Pair<Integer,Integer> localCoords, Organism object)
    {
         this.sector[localCoords.getX()][localCoords.getY()] = object;
    }

    public Organism getFieldGlobal(Pair<Integer,Integer> globalCoords)
    {
        return this.getFieldLocal(toLocalCoords(globalCoords));
    }

    public void setFieldGlobal(Pair<Integer,Integer> globalCoords, Organism object)
    {
        Pair<Integer,Integer> localCoords = toLocalCoords(globalCoords);
        this.setFieldLocal(localCoords, object);
    }

    public Pair<Integer, Integer> getID() {
        return this.ID;
    }
    public Pair<Integer, Integer> getSize() {
        return this.size;
    }

    public void addOrganism(Organism organism)
    {
        this.organisms.get(organism.getValue(Values.INITIATIVE)).add(organism);
        this.setFieldGlobal(organism.getCoords(), organism);
    }

    public ArrayList<ArrayList<Organism>> getOrganisms()
    {
        return organisms;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
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
