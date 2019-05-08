import bin.system.DataLoader;
import bin.world.World;

import static bin.world.World.WorldAPI;

public class Main {

    public static void main(String[] args) {
        new DataLoader(); //loads configs
        new World();
        System.out.println(WorldAPI.getPopulation());
        System.out.println(WorldAPI.getLog());
    }
}
