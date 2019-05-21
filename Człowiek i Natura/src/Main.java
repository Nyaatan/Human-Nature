import bin.system.API;
import bin.system.Commander;
import bin.system.GameSystem;

public class Main {

    public static void main(String[] args) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Start();
        Commander com = new Commander();
        //System.out.println(dataLoader.getItemDescription(ItemName.WOOD).getY());

        //GlobalSettings.getWorld(0).makeTurn();
        System.out.println(API.worldAPI.getPopulation(0));
    }
}
