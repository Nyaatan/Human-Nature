package bin.system.chunkMap;

import lib.API;
import lib.Pair;

import java.util.ArrayList;

public class ChunkMapGen {
    private int renderingDistance = API.systemAPI.RENDERING_DISTANCE;
    ChunkMap chunkMap = new ChunkMap();
    ArrayList<Pair<Integer,Integer>> toCompute = new ArrayList<>();

    public ChunkMap generate()
    {
        for(int i=-renderingDistance;i<renderingDistance;++i)
        {
            for(int j=-renderingDistance;j<renderingDistance;++j)
            {
                toCompute.add(new Pair<>(i,j));
                if(toCompute.size() == Runtime.getRuntime().availableProcessors()-1) {
                    this.compute();
                }
            }
        }
        if(toCompute.size() > 0)
        {
            this.compute();
        }
        return chunkMap;
    }

    private void compute()
    {
        ArrayList<ChunkGen> chunkGens = new ArrayList<>();
        for(Pair<Integer,Integer> IDs : this.toCompute)
        {
            chunkGens.add(new ChunkGen(IDs));
        }
        for(ChunkGen sector : chunkGens)
        {
            sector.fork();
        }
        for(ChunkGen chunkGen : chunkGens)
        {
            this.chunkMap.setChunk((Chunk) chunkGen.join(), chunkGen.ID);
        }
        this.toCompute.clear();
    }
}
