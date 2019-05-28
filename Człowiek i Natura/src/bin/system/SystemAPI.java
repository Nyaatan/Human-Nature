package bin.system;

import bin.system.dataLoader.DataLoader;
import bin.world.World;
import lib.Enums;

import java.util.ArrayList;

public class SystemAPI {

    //GLOBAL SETTINGS
    public int MAX_INITIATIVE;
    public int CHUNK_SIZE;
    public int RENDERING_DISTANCE;

    void setVariables()
    {
        MAX_INITIATIVE = GameSystem.globalSettings.MAX_INITIATIVE;
        CHUNK_SIZE = GameSystem.globalSettings.CHUNK_SIZE;
        RENDERING_DISTANCE = GameSystem.globalSettings.RENDERING_DISTANCE;
    }

    public DataLoader DataLoader()
    {
        return GlobalSettings.dataLoader;
    }

    public void addWorld(World world) {
        GameSystem.globalSettings.addWorld(world);
    }

    public World getWorld() {return GameSystem.globalSettings.getWorld();}

    public Enums.Language getLanguage() { return GameSystem.globalSettings.getLanguage(); }

    public void setLanguage(Enums.Language language) { GameSystem.globalSettings.setLanguage(language); }
    //END GS
    /////////
    //GAME SYSTEM
    public ArrayList<String> getLog() { return GameSystem.getLog(); }

    public void addLog(String log) {
        GameSystem.addLog(log);
    }
    //END GAME SYSTEM

}
