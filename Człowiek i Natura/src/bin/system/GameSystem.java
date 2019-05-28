package bin.system;

import bin.ui.UI;
import lib.API;

import java.util.ArrayList;

public class GameSystem {

    static GlobalSettings globalSettings;
    UI ui;

    public MenuActionHandler menuActionHandler;

    public GameSystem()
    {
        log = new ArrayList<>();
        new API();
        globalSettings = new GlobalSettings();
        API.systemAPI.setVariables();
        ui = new UI();
    }

    private static ArrayList<String> log;

    public void Start() {
        menuActionHandler = new MenuActionHandler(this);
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
