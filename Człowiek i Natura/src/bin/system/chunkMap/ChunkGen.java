package bin.system.chunkMap;

import lib.API;
import lib.Enums;
import lib.Pair;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class ChunkGen extends RecursiveTask {
    Pair<Integer,Integer> ID;
    private Chunk chunk;

    ChunkGen(){}
    ChunkGen(Pair<Integer,Integer> ID){
        this.ID = ID;
    }

    Chunk generate(Pair<Integer, Integer> ID)
    {
        chunk = new Chunk(ID);

        for(int i=0;i< API.systemAPI.CHUNK_SIZE;++i)
        {
            for(int j=0;j<API.systemAPI.CHUNK_SIZE;++j)
            {
                spawn(i,j);
            }
        }

        return chunk;
    }

    private void spawn(int i, int j)
    {
        for(Enums.Species.AllSpecies species : Enums.Species.AllSpecies.values()) {
            if(!species.equals(Enums.Species.AllSpecies.HUMAN)) {
                if (ThreadLocalRandom.current().nextInt(1000) <
                        Integer.parseInt(API.dataLoaderAPI.getBlockConfig(species.toString(),
                                "species").get("spawn_rate").get(0))) {//chance of spawning: specimen.spawn_rate%
                    chunk.add(new Pair<>(i, j), API.worldSPI.createOrganism(species, chunk.toGlobalCoords(new Pair<>(i, j)), ID)); //make random organism
                    break;
                } else {
                    chunk.add(new Pair<>(i, j), null);
                }
            }
        }
    }

    @Override
    protected Chunk compute() {
        return generate(this.ID);
    }
}
