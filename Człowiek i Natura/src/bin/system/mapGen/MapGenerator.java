package bin.system.mapGen;

import lib.Pair;
import bin.system.chunkMap.ChunkMap;

import java.util.ArrayList;

public class MapGenerator  {
    private ChunkMap map;
    private ArrayList<Pair<Integer,Integer>> toCompute;
    private int worldID;

    public MapGenerator(int worldID,Pair<Integer,Integer> mapSize, Pair<Integer,Integer> sectorSize)
    {
        this.toCompute = new ArrayList<>();
        this.map = new ChunkMap(mapSize,sectorSize);
        this.worldID = worldID;
    }

    public ChunkMap generate()
    {
        for(Pair<Integer,Integer> ID : this.map.getIDSet())
        {
            this.toCompute.add(ID);
            if(this.toCompute.size() == Runtime.getRuntime().availableProcessors()-1)
            {
                this.compute();
            }
        }
        if(this.toCompute.size() > 0)
        {
            this.compute();
        }
        return this.map;
    }

    private void compute()
    {
        ArrayList<SectorGen> sectorGens = new ArrayList<>();
        for(Pair<Integer,Integer> IDs : this.toCompute)
        {
            sectorGens.add(new SectorGen(this.map.getSectorByID(IDs), IDs, this.worldID));
        }
        for(SectorGen sector : sectorGens)
        {
            sector.fork();
        }
        for(SectorGen sector : sectorGens)
        {
            this.map.setSector(sector.join(), sector.getID());
        }
        this.toCompute.clear();
    }

}
