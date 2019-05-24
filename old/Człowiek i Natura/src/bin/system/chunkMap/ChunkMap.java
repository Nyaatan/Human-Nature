package bin.system.chunkMap;

import bin.world.organism.Organism;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ChunkMap {//todo nieskończona proceduralnie generowana mapa

    private Pair<Integer,Integer> sectorSize;

    private HashMap<Pair<Integer,Integer>, Chunk> sectorMap = new HashMap<>();

    public ChunkMap(Pair<Integer,Integer> mapSize, Pair<Integer,Integer> sectorSize)
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
                        this.sectorMap.put(constructorIterator.copy(), new Chunk(sectorSize, constructorIterator.copy()));

                    }
                    else if(Math.floorMod(mapSize.getY(),sectorSize.getY()) != 0) {
                        this.sectorMap.put(constructorIterator.copy(), new Chunk(
                                sectorSize.getX(), Math.floorMod(mapSize.getY(), sectorSize.getY()), constructorIterator.copy()));
                    }
                }
                else
                {
                    if(!constructorIterator.getY().equals(sectorBounds.getY()) &&
                            Math.floorMod(mapSize.getX(),sectorSize.getX()) != 0) {
                        this.sectorMap.put(constructorIterator.copy(), new Chunk(
                                Math.floorMod(mapSize.getX(),sectorSize.getX()), sectorSize.getY(), constructorIterator.copy()));
                    }
                    else if(Math.floorMod(mapSize.getX(),sectorSize.getX()) != 0)
                        this.sectorMap.put(constructorIterator.copy(), new Chunk(
                            Math.floorMod(mapSize.getX(),sectorSize.getX()), Math.floorMod(mapSize.getY(),sectorSize.getY()),
                                constructorIterator.copy()) );
                }
                //System.out.println(this.chunkMap.get(constructorIterator).getSize());

                constructorIterator = new Pair<>(constructorIterator.getX(), constructorIterator.getY()+1);
            }

            constructorIterator = new Pair<>(constructorIterator.getX()+1, constructorIterator.getY());
        }
    }

    public ChunkMap(ChunkMap map) {
        this.sectorMap = map.toHashMap();
        this.sectorSize = map.sectorSize;
    }

    public Set<Pair<Integer,Integer>> getIDSet() { return this.sectorMap.keySet(); }

    public Chunk getSectorByID(Pair<Integer,Integer> ID) { return this.sectorMap.get(ID); }

    public Chunk getSectorByCoords(Pair<Integer,Integer> coords)
    {
        Pair<Integer,Integer> sectorID = new Pair<>(
                (int) Math.round(Math.floor((double) coords.getX() / (double)this.sectorSize.getX())),
                (int) Math.round(Math.floor((double) coords.getY() / (double)this.sectorSize.getY())));
        System.out.println(sectorMap.get(sectorID));
        return this.sectorMap.get(sectorID);
    }

    public void setSector(Chunk chunk, Pair<Integer,Integer> ID) {this.sectorMap.replace(ID, chunk);}

    public Organism getField(Pair<Integer,Integer> coords)
    {
        return this.getSectorByCoords(coords).getFieldGlobal(coords);
    }

    public void setField(Pair<Integer,Integer> coords, Organism object)
    {
        this.getSectorByCoords(coords).setFieldGlobal(coords,object);
    }

    public HashMap<Pair<Integer,Integer>, Chunk> toHashMap() { return this.sectorMap; }

    public Pair<Integer,Integer> getSectorSize() {return this.sectorSize;}

    public Collection<Chunk> values()
    {
        return this.sectorMap.values();
    }

    public HashMap<Enums.Species.AllSpecies,Integer> getPopulation()
    {
        HashMap<Enums.Species.AllSpecies,Integer> result = new HashMap<>();
        ArrayList<ArrayList<ArrayList<Organism>>> organismsExtended = new ArrayList<>();
        for(Chunk chunk : this.sectorMap.values())
        {
            organismsExtended.add(chunk.getOrganisms());
        }

        for(ArrayList<ArrayList<Organism>> organisms : organismsExtended) {
            for (ArrayList<Organism> organismList : organisms) {
                for (Organism organism : organismList) {
                    //if result has the specimen, increment the count, else add specimen to result
                    if (result.keySet().contains(organism.getSpecies())) {
                        result.put(organism.getSpecies(), result.get(organism.getSpecies()) + 1);
                    } else result.put(organism.getSpecies(), 1);
                }
            }
        }
        return result;
    }
}