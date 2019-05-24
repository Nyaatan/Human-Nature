package bin.system.TurnComputer;

import bin.system.chunkMap.Chunk;
import bin.system.chunkMap.ChunkMap;

import java.util.ArrayList;
import java.util.Collection;

public class TurnComputer{
    private Collection<Chunk> chunks;
    private ChunkMap map;

    public TurnComputer(ChunkMap map)
    {
        this.map = map;
    }

    public void compute()
    {//TODO NIE DZIAŁA, dodać sectormap result i zwracać
        ArrayList<SectorComputer> toCompute = new ArrayList<>();
        for(Chunk chunk : this.chunks)
        {
            toCompute.add(new SectorComputer(chunk));
            if(toCompute.size() == Runtime.getRuntime().availableProcessors()-1)
            {
                sectorCompute(toCompute);
            }
        }
        if(toCompute.size() > 0)
        {
            sectorCompute(toCompute);
        }
    }

    private void sectorCompute(ArrayList<SectorComputer> toCompute)
    {
        for(SectorComputer sectorComputer : toCompute)
        {
            sectorComputer.fork();
        }
        for(SectorComputer sectorComputer : toCompute)
        {
            sectorComputer.join();
        }
        toCompute.clear();
    }
}
