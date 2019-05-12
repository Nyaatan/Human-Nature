import bin.enums.item.ItemName;
import bin.system.DataLoader;
import bin.system.GameSystem;
import bin.world.WorldAPI;
import bin.world.organism.Organism;

public class Main {

    public static void main(String[] args) {
        GameSystem.Initialize();
        GameSystem.Start();
        System.out.println(DataLoader.getItemDescription(ItemName.WOOD).getY());
        System.out.println(WorldAPI.getPopulation(0));
        Organism[][] map = WorldAPI.getMap(0);
    }
}
