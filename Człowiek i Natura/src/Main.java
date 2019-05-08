import bin.world.World;

import static bin.world.World.WorldAPI;

public class Main {

    public static void main(String[] args) {
        new World();
        System.out.println(WorldAPI.getPopulation());
        WorldAPI.getOrganisms().get(1).get(0).die();
        System.out.println(WorldAPI.getPopulation());
        System.out.println(WorldAPI.getLog());
    }
}
