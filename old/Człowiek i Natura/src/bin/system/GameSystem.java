package bin.system;

import bin.ui.UI;
import bin.world.World;

import java.util.ArrayList;

public class GameSystem {

    static GlobalSettings globalSettings;
    UI ui;

    public GameSystem()
    {
        log = new ArrayList<>();
        new API();
        globalSettings = new GlobalSettings();
        ui = new UI();
    }

    private static ArrayList<String> log;

    public void Start() { new World(); }
    public void Stop() {Thanos.snap();}

    static ArrayList<String> getLog() { return log; }

    static void addLog(String log) {
        GameSystem.log.add(log);
    }

    private static class Thanos
    {
        public static void snap()
        {
            API.systemAPI.getWorlds().clear();
        }
    }

}
