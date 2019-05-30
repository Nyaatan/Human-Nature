import bin.system.GameSystem;
import lib.API;

public class Main {

    public static void main(String[] args) throws Exception {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Start();
        API.systemAPI.getWorld().save();
    }
}
