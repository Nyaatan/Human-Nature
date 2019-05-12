package bin.interfaces;

import bin.world.organism.Organism;

public interface InteractionsActive {
    void interact(Organism interacted);
    void move();
}
