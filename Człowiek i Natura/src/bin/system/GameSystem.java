package bin.system;

import bin.ui.Menu;
import lib.API;

import java.util.ArrayList;

public class GameSystem {

    static GlobalSettings globalSettings;

    Menu menu;

    public GameSystem()
    {
        log = new ArrayList<>();
        new API();
        globalSettings = new GlobalSettings();
        API.systemAPI.setVariables();
        menu = new Menu();
    }

    private static ArrayList<String> log;

    public void Start() throws Exception {
        menu.setGameSystem(this);
        menu.Launch();
    }
    public void Stop() {Thanos.snap();}

    static ArrayList<String> getLog() { return log; }

    static void addLog(String log) {
        GameSystem.log.add(log);
    }

    private static class Thanos
    {
        static void snap()
        {
            API.systemAPI.addWorld(null);
        }
    }

}
