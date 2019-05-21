package bin.system;

import bin.system.dataLoader.DataLoader;
import bin.world.World;
import lib.Enums;

import java.util.ArrayList;

public class GlobalSettings {
    final int MAX_INITIATIVE = 15;

    public static DataLoader dataLoader;

    private Enums.Language language;

    private ArrayList<World> worlds = new ArrayList<>();

    void addWorld(World world) {this.worlds.add(world);}
    World getWorld(int ID) {return worlds.get(ID);}
    ArrayList<World> getWorlds() {return worlds;}
    int getWorldID(World world) {return worlds.indexOf(world);}


    Enums.Language getLanguage() {
        return language;
    }

    void setLanguage(Enums.Language language) { this.language = language; }

    GlobalSettings()
    {
        setLanguage(Enums.Language.valueOf(System.getProperty("user.language").toUpperCase().split("-")[0]));
        dataLoader = new DataLoader(language);
    }
}
