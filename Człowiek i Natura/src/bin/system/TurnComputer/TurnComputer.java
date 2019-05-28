package bin.system.TurnComputer;

import bin.system.chunkMap.Chunk;
import bin.system.chunkMap.ChunkMap;
import bin.world.organism.Organism;
import lib.API;
import lib.CommandRefusedException;
import lib.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static lib.Enums.Species.AllSpecies.HUMAN;

public class TurnComputer{
    private Collection<Chunk> chunks;
    private ChunkMap map;

    public TurnComputer(ChunkMap map)
    {
        this.map = map;
        chunks = map.getMap().values();
    }

    public ChunkMap compute()
    {
        ArrayList<ChunkComputer> toCompute = new ArrayList<>();
        for(Chunk chunk : this.chunks)
        {
            toCompute.add(new ChunkComputer(chunk));
            if(toCompute.size() == Runtime.getRuntime().availableProcessors()-1)
            {
                sectorCompute(toCompute);
            }
        }
        if(toCompute.size() > 0)
        {
            sectorCompute(toCompute);
        }
        for(Chunk chunk : chunks)
        {
            map.setChunk(chunk, chunk.getID());
        }
        return map;
    }

    private void sectorCompute(ArrayList<ChunkComputer> toCompute)
    {
        for(ChunkComputer chunkComputer : toCompute)
        {
            chunkComputer.fork();
        }
        for(ChunkComputer chunkComputer : toCompute)
        {
            chunkComputer.join();
        }
        toCompute.clear();
    }

    public void computeTurn()
    {
        Pair<Integer,Integer> centerID = map.getChunkByCoords(API.worldSPI.getHuman().getCoords()).getID();

        for(Chunk chunk : chunks)
        {
            if(Math.abs(centerID.getX()-chunk.getID().getX())<API.systemAPI.RENDERING_DISTANCE-1 &&
                    Math.abs(centerID.getY()-chunk.getID().getY())<API.systemAPI.RENDERING_DISTANCE-1) {

                chunk.setOrganisms(computeChunk(new Chunk(chunk)));
            }
        }
        for(Organism organism : map.getTransfers())
        {
            if(map.getField(organism.getCoords())!=null) organism.die(map.getField(organism.getCoords()));
            else map.setField(organism.getCoords(),organism);
        }
    }

    private ArrayList<ArrayList<Organism>> computeChunk(Chunk chunk)
    {

        ArrayList<ArrayList<Organism>> toCompute = new ArrayList<>(chunk.getOrganisms());
        Collections.reverse(toCompute);
        ArrayList<ArrayList<Organism>> newOrganisms = new ArrayList<>(toCompute);

        for(int i=0;i<15;++i)
        {

            for(Organism organism : new ArrayList<>(toCompute.get(i)))
            {
                if(toCompute.get(i).indexOf(organism)>-1) {
                    organism = newOrganisms.get(i).get(toCompute.get(i).indexOf(organism));
                    if (organism.getSpecies() != HUMAN) {
                        try {
                            organism.move();
                        } catch (CommandRefusedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        Collections.reverse(newOrganisms);
        return newOrganisms;
    }
}
