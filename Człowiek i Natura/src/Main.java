import bin.system.API;
import bin.system.Commander;
import bin.system.GameSystem;
import lib.Pair;

public class Main {

    public static void main(String[] args) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Start();
        Commander com = new Commander();
        //System.out.println(dataLoader.getItemDescription(ItemName.WOOD).getY());

        //GlobalSettings.getWorld(0).makeTurn();
        System.out.println(API.worldAPI.getPopulation());
        API.worldAPI.getMap().changeCenter(new Pair<>(1,0));
        System.out.println(API.worldAPI.getPopulation());
        API.worldAPI.getMap().changeCenter(new Pair<>(0,0));
        System.out.println(API.worldAPI.getPopulation());
    }
}
