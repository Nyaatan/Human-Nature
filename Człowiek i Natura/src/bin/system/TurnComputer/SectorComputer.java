package bin.system.TurnComputer;

import bin.system.chunkMap.Chunk;
import bin.world.organism.Organism;
import lib.CommandRefusedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.RecursiveAction;

public class SectorComputer extends RecursiveAction {

    private Chunk chunk;

    public SectorComputer(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    protected void compute() {
        ArrayList<ArrayList<Organism>> toCompute = new ArrayList<>(this.chunk.getOrganisms());
        Collections.reverse(toCompute);
        for (ArrayList<Organism> computedInitiative : toCompute) {
            for (Organism organism : computedInitiative) {
                try {
                    organism.move();
                } catch (CommandRefusedException e) {
                    System.out.println("DUPA");
                }
            }
        }
    }
}
