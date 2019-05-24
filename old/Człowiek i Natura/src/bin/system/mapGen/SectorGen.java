package bin.system.mapGen;

import bin.system.API;
import lib.Enums;
import lib.Pair;
import bin.system.chunkMap.Chunk;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class SectorGen extends RecursiveTask<Chunk> {
    @Override
    protected Chunk compute() {
        Pair<Integer,Integer> sectorSize = this.chunk.getSize();
        for(int i=0;i<sectorSize.getX();++i)
        {
            for(int j=0;j<sectorSize.getY();++j)
            {
                Enums.Species.AllSpecies species = Enums.Species.AllSpecies.HUMAN; //get random non-human specimen
                while(species.equals(Enums.Species.AllSpecies.HUMAN)) species =
                        Enums.Species.AllSpecies.values()[
                                ThreadLocalRandom.current().nextInt(Enums.Species.AllSpecies.values().length)];
                Pair <Integer,Integer> mapSize = new Pair<>( Integer.parseInt(
                        API.dataLoaderAPI.getConfig("map_size", "config").get(0)),
                        Integer.parseInt(
                                API.dataLoaderAPI.getConfig("map_size", "config").get(1)));

                if(ThreadLocalRandom.current().nextInt(
                        mapSize.getX()*mapSize.getY() ) < (
                        Math.ceil(mapSize.getX()*mapSize.getY() *
                                Integer.parseInt(API.dataLoaderAPI.getBlockConfig(species.toString(),
                                "species").get("spawn_rate").get(0))/100) ) )
                {//chance of spawning: map_fields * specimen.spawn_rate/100

                    this.chunk.addOrganism(API.worldSPI.createOrganism(this.worldID, species ,
                            this.chunk.toGlobalCoords(new Pair<>(i,j)), this.ID)); //make random organism
                }
                else this.chunk.setFieldLocal(new Pair<>(i,j), null);
            }
        }
        return this.chunk;
    }

    private Chunk chunk;
    private Pair<Integer,Integer> ID;
    private int worldID;

    public SectorGen(Chunk chunk, Pair<Integer,Integer> ID, int worldID)
    {
        this.ID = ID;
        this.chunk = chunk;
        this.worldID = worldID;
    }


    public Pair<Integer, Integer> getID() {
        return ID;
    }
}
