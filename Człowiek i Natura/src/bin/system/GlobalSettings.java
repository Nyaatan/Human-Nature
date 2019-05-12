package bin.system;

import bin.enums.settings.Language;
import bin.world.World;

import java.util.ArrayList;

public class GlobalSettings {
    private static Language language;

    private static ArrayList<World> worlds = new ArrayList<>();

    public static void addWorld(World world) {worlds.add(world);}
    public static World getWorld(int ID) {return worlds.get(ID);}
    public static int getWorldID(World world) {return worlds.indexOf(world);}

    public static Language getLanguage() {
        return language;
    }

    public static void setLanguage(Language language) { GlobalSettings.language = language; }

    public GlobalSettings()
    {
        setLanguage(Language.valueOf(System.getProperty("user.language").toUpperCase().split("-")[0]));
    }
}
