package bin.system.sectorMap;

import bin.system.Pair;
import bin.world.organism.Organism;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class SectorMap {

    private Pair<Integer,Integer> sectorSize;

    private HashMap<Pair<Integer,Integer>,Sector> sectorMap = new HashMap<>();

    public SectorMap(Pair<Integer,Integer> mapSize, Pair<Integer,Integer> sectorSize)
    {
        this.sectorSize = sectorSize;
        Pair<Integer,Integer> constructorIterator = new Pair<>(0,0);
        Pair<Integer,Integer> sectorBounds;
        sectorBounds = new Pair<>(
                (int) Math.round(Math.floor((double) mapSize.getX() / (double)this.sectorSize.getX())),
                (int) Math.round(Math.floor((double) mapSize.getY() / (double)this.sectorSize.getY())));
        //System.out.println(secorBounds.getX());
        //System.out.println(secorBounds.getY());

        while(constructorIterator.getX() < sectorBounds.getX())
        {
            constructorIterator.setY(0);
            while(constructorIterator.getY() < sectorBounds.getY())
            {
                if(!constructorIterator.getX().equals(sectorBounds.getX()))
                {
                    if(!constructorIterator.getY().equals(sectorBounds.getY())) {
                        this.sectorMap.put(constructorIterator.copy(), new Sector(sectorSize, constructorIterator.copy()));

                    }
                    else if(Math.floorMod(mapSize.getY(),sectorSize.getY()) != 0) {
                        this.sectorMap.put(constructorIterator.copy(), new Sector(
                                sectorSize.getX(), Math.floorMod(mapSize.getY(), sectorSize.getY()), constructorIterator.copy()));
                    }
                }
                else
                {
                    if(!constructorIterator.getY().equals(sectorBounds.getY()) &&
                            Math.floorMod(mapSize.getX(),sectorSize.getX()) != 0) {
                        this.sectorMap.put(constructorIterator.copy(), new Sector(
                                Math.floorMod(mapSize.getX(),sectorSize.getX()), sectorSize.getY(), constructorIterator.copy()));
                    }
                    else if(Math.floorMod(mapSize.getX(),sectorSize.getX()) != 0)
                        this.sectorMap.put(constructorIterator.copy(), new Sector(
                            Math.floorMod(mapSize.getX(),sectorSize.getX()), Math.floorMod(mapSize.getY(),sectorSize.getY()),
                                constructorIterator.copy()) );
                }
                //System.out.println(this.sectorMap.get(constructorIterator).getSize());

                constructorIterator = new Pair<>(constructorIterator.getX(), constructorIterator.getY()+1);
            }

            constructorIterator = new Pair<>(constructorIterator.getX()+1, constructorIterator.getY());
        }
    }

    public SectorMap(SectorMap map) {
        this.sectorMap = map.toHashMap();
    }

    public Set<Pair<Integer,Integer>> getIDSet() { return this.sectorMap.keySet(); }

    public Sector getSectorByID(Pair<Integer,Integer> ID) { return this.sectorMap.get(ID); }

    public Sector getSectorByCoords(Pair<Integer,Integer> coords)
    {
        Pair<Integer,Integer> sectorID = new Pair<>(
                (int) Math.round(Math.floor((double) coords.getX() / (double)this.sectorSize.getX())),
                (int) Math.round(Math.floor((double) coords.getY() / (double)this.sectorSize.getY())));
        return this.sectorMap.get(sectorID);
    }

    public void setSector(Sector sector, Pair<Integer,Integer> ID) {this.sectorMap.replace(ID, sector);}

    public Organism getField(Pair<Integer,Integer> coords)
    {
        return this.getSectorByCoords(coords).getFieldGlobal(coords);
    }

    public void setField(Pair<Integer,Integer> coords, Organism object)
    {
        this.getSectorByCoords(coords).setFieldGlobal(coords,object);
    }

    public HashMap<Pair<Integer,Integer>,Sector> toHashMap() { return this.sectorMap; }

    public Pair<Integer,Integer> getSectorSize() {return this.sectorSize;}

    public Collection<Sector> values()
    {
        return this.sectorMap.values();
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