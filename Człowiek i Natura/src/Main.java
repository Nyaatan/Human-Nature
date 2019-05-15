import bin.system.GameSystem;
import bin.world.WorldAPI;

public class Main {

    public static void main(String[] args) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Initialize();
        gameSystem.Start();
        //System.out.println(dataLoader.getItemDescription(ItemName.WOOD).getY());
        System.out.println(WorldAPI.getPopulation(0));
    }
}
