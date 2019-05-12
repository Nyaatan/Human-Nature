package bin.system;

import bin.world.World;
import bin.world.WorldAPI;
import bin.world.WorldSPI;

import java.util.ArrayList;

public class GameSystem {
    public static void Initialize()
    {
        new GlobalSettings();
        new DataLoader();
        new WorldAPI();
        new WorldSPI();
    }

    private static ArrayList<String> log;

    public static void Start() { new World(); }

    public static ArrayList<String> getLog() { return log; }

    public static void addLog(String log) {
        GameSystem.log.add(log);
    }
}
