package bin.system;

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
    private ArrayList<Organism> computed;

    public TurnComputer(ChunkMap map)
    {
        this.map = map;
        chunks = map.getMap().values();
    }

    public void computeTurn() throws CommandRefusedException {
        Pair<Integer,Integer> centerID = map.getChunkByCoords(API.worldSPI.getHuman().getCoords()).getID();
        computed = new ArrayList<>();

        for(Chunk chunk : chunks)
        {
            if(Math.abs(centerID.getX()-chunk.getID().getX())<API.systemAPI.RENDERING_DISTANCE-1 &&
                    Math.abs(centerID.getY()-chunk.getID().getY())<API.systemAPI.RENDERING_DISTANCE-1) {

                chunk.setOrganisms(computeChunk(new Chunk(chunk)));
            }
        }
        for(Organism organism : map.getTransfers())
        {
            try {
                if (map.getField(organism.getCoords()) != null) organism.interact(map.getField(organism.getCoords()));
                else map.setField(organism.getCoords(), organism);
            } catch (NullPointerException e) {
                try{
                    map.setField(organism.getOldCoords(), organism);
                } catch (NullPointerException ex) {
                    organism.commitSeppuku();
                }
            }
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
                    if (organism.getSpecies() != HUMAN && !computed.contains(organism)) {
                        try {
                            organism.move();
                            computed.add(organism);
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
