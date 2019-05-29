package bin.system.chunkMap;

import bin.world.organism.Organism;
import lib.API;
import lib.Enums;
import lib.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Math.abs;

public class ChunkMap implements Serializable {
    HashMap<Pair<Integer,Integer>, Chunk> chunkMap;
    private int renderingDistance;
    private Pair<Integer,Integer> centerID;
    private ChunkDumper chunkDumper;
    private ChunkGen chunkGen;
    private ArrayList<Organism> transfers;

    public ChunkMap()
    {
        chunkGen = new ChunkGen();
        chunkDumper = new ChunkDumper();
        chunkMap = new HashMap<>();
        this.renderingDistance = API.systemAPI.RENDERING_DISTANCE;
        this.centerID = new Pair<>(0,0);
        this.transfers = new ArrayList<>();
    }

    private Chunk get(Pair<Integer,Integer> ID)
    {
        for(Pair<Integer,Integer> key : chunkMap.keySet())
        {
            if(key.equals(ID))
            {
                return chunkMap.get(key);
            }
        }
        return null;
    }

    private Pair<Integer,Integer> coordsToID(Pair<Integer,Integer> coords)
    {
        return new Pair<>(coords.getX()/API.systemAPI.CHUNK_SIZE, coords.getY()/API.systemAPI.CHUNK_SIZE);
    }

    public Organism getField(Pair<Integer,Integer> coords)
    {
        Pair<Integer,Integer> chunkID = new Pair<>((int) Math.floor((double)coords.getX()/(double)API.systemAPI.CHUNK_SIZE),
                (int) Math.floor((double)coords.getY()/(double)API.systemAPI.CHUNK_SIZE));
        return get(chunkID).get(new Pair<>(coords.getX()%API.systemAPI.CHUNK_SIZE, coords.getY()%API.systemAPI.CHUNK_SIZE));
    }

    public void setField(Pair<Integer,Integer> coords, Organism organism)
    {
        Pair<Integer,Integer> ID = coordsToID(coords);
        if(get(ID)!=null)
            get(ID).add(Chunk.toLocalCoords(coords), organism);
    }

    public Chunk getChunkByCoords(Pair<Integer,Integer> coords)
    {
        Pair<Integer,Integer> ID = coordsToID(coords);
        return getChunkByID(ID);
    }

    public Chunk getChunkByID(Pair<Integer,Integer> ID) {return get(ID);}

    public void changeCenter(Pair<Integer,Integer> newCenterID)
    {
        centerID = newCenterID;
        HashSet<Pair<Integer,Integer>> keys = new HashSet<>(chunkMap.keySet());
        for(Pair<Integer,Integer> ID : keys)
        {
            if(abs(ID.getX()-newCenterID.getX())>=renderingDistance || abs(ID.getY()-newCenterID.getY())>=renderingDistance)
            {
                chunkDumper.Dump(chunkMap.get(ID));
                chunkMap.remove(ID);
            }
        }
        for(int x=newCenterID.getX()-renderingDistance;x<newCenterID.getX()+renderingDistance;++x)
        {
            for(int y=newCenterID.getY()-renderingDistance;y<newCenterID.getY()+renderingDistance;++y)
            {
                Pair<Integer,Integer> newID = new Pair<>(x,y);
                if(!contains(newID))
                {
                    Chunk newChunk = chunkDumper.load(newID);
                    if(newChunk==null) newChunk = chunkGen.generate(newID);
                    chunkMap.put(newID,newChunk);
                }
            }
        }
    }

    public void setChunk(Chunk chunk, Pair<Integer,Integer> ID)
    {
        this.chunkMap.put(ID,chunk);
    }

    public HashMap<Enums.Species.AllSpecies,Integer> getPopulation()
    {
        HashMap<Enums.Species.AllSpecies,Integer> result = new HashMap<>();
        ArrayList<ArrayList<ArrayList<Organism>>> organismsExtended = new ArrayList<>();
        for(Chunk chunk : this.chunkMap.values())
        {
            organismsExtended.add(chunk.getOrganisms());
        }

        for(ArrayList<ArrayList<Organism>> organisms : organismsExtended) {
            for (ArrayList<Organism> organismList : organisms) {
                for (Organism organism : organismList) {
                    //if result has the specimen, increment the count, else add specimen to result
                    if (organism!=null && result.keySet().contains(organism.getSpecies())) {
                        result.put(organism.getSpecies(), result.get(organism.getSpecies()) + 1);
                    } else result.put(organism.getSpecies(), 1);
                }
            }
        }
        return result;
    }

    private boolean contains(Pair<Integer,Integer> ID)
    {
        for(Pair<Integer,Integer> key : chunkMap.keySet())
        {
            if(key.equals(ID)) return true;
        }
        return false;
    }

    public HashMap<Pair<Integer,Integer>, Chunk> getMap()
    {
        return chunkMap;
    }

    public void addTransfer(Organism organism)
    {
        transfers.add(organism);
    }

    public ArrayList<Organism> getTransfers() {
        return transfers;
    }

    public Pair<Integer,Integer> getCenterID()
    {
        return this.centerID;
    }

    public Chunk getCenterChunk()
    {
        return this.getChunkByID(getCenterID());
    }
}
