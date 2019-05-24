package bin.system;

import bin.system.dataLoader.DataLoader;
import bin.world.World;
import lib.Enums;

import java.util.ArrayList;

public class SystemAPI {

    //GLOBAL SETTINGS
    public static int MAX_INITIATIVE() {return GameSystem.globalSettings.MAX_INITIATIVE;}

    public DataLoader DataLoader()
    {
        return GlobalSettings.dataLoader;
    }

    public void addWorld(World world) {
        GameSystem.globalSettings.addWorld(world);
    }

    public World getWorld(int ID) {return GameSystem.globalSettings.getWorld(ID);}

    public int getWorldID(World world) {return GameSystem.globalSettings.getWorldID(world);}

    public Enums.Language getLanguage() { return GameSystem.globalSettings.getLanguage(); }

    public void setLanguage(Enums.Language language) { GameSystem.globalSettings.setLanguage(language); }

    ArrayList<World> getWorlds() {return GameSystem.globalSettings.getWorlds();}
    //END GS
    /////////
    //GAME SYSTEM
    public ArrayList<String> getLog() { return GameSystem.getLog(); }

    public void addLog(String log) {
        GameSystem.addLog(log);
    }
    //END GAME SYSTEM
}
