package bin.system.chunkMap;

import bin.system.API;
import lib.Enums;
import lib.Pair;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class ChunkGen extends RecursiveTask {
    Pair<Integer,Integer> ID;

    ChunkGen(){}
    ChunkGen(Pair<Integer,Integer> ID){
        this.ID = ID;
    }

    Chunk generate(Pair<Integer, Integer> ID)
    {
        Chunk chunk = new Chunk(ID);

        for(int i=0;i< API.systemAPI.CHUNK_SIZE;++i)
        {
            for(int j=0;j<API.systemAPI.CHUNK_SIZE;++j)
            {
                Enums.Species.AllSpecies species = Enums.Species.AllSpecies.HUMAN; //get random non-human specimen
                while(species.equals(Enums.Species.AllSpecies.HUMAN)||species.equals(Enums.Species.AllSpecies.GRASS)) species =
                        Enums.Species.AllSpecies.values()[
                                ThreadLocalRandom.current().nextInt(Enums.Species.AllSpecies.values().length)];

                if(ThreadLocalRandom.current().nextInt(100) <
                                Integer.parseInt(API.dataLoaderAPI.getBlockConfig(species.toString(),
                                        "species").get("spawn_rate").get(0)))
                {//chance of spawning: specimen.spawn_rate%

                    chunk.add(new Pair<>(i,j), API.worldSPI.createOrganism(species , chunk.toGlobalCoords(new Pair<>(i,j)), ID)); //make random organism
                }
                else chunk.add(new Pair<>(i,j), null);
            }
        }

        return chunk;
    }

    @Override
    protected Chunk compute() {
        return generate(this.ID);
    }
}
