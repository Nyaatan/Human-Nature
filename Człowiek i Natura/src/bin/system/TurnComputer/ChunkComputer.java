package bin.system.TurnComputer;

import bin.system.chunkMap.Chunk;
import bin.world.organism.Organism;
import lib.CommandRefusedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.RecursiveAction;

import static lib.Enums.Species.AllSpecies.HUMAN;

public class ChunkComputer extends RecursiveAction {

    private Chunk chunk;

    public ChunkComputer(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    protected void compute() {
        ArrayList<ArrayList<Organism>> toCompute = new ArrayList<>(this.chunk.getOrganisms());
        Collections.reverse(toCompute);
        for (ArrayList<Organism> computedInitiative : toCompute) {
            for (Organism organism : computedInitiative) {
                try {
                    if(organism.getSpecies()!=HUMAN) organism.move();
                } catch (CommandRefusedException e) {
                    System.out.println("DUPA");
                }
            }
        }
    }
}
