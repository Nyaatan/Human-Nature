package bin.system;

import bin.system.dataLoader.DataLoader;
import bin.world.World;
import lib.API;
import lib.Enums;

import java.io.File;

public class GlobalSettings {
    int MAX_INITIATIVE = 15;
    int CHUNK_SIZE = 16;
    int RENDERING_DISTANCE = 10;

    public static String worldName = null;

    public static DataLoader dataLoader;

    private Enums.Language language;

    private World world;

    void addWorld(World world) {
        if(world!=null) makeWorldFolder();
        this.world = world;
    }
    World getWorld() {return world;}

    Enums.Language getLanguage() {
        return language;
    }

    void setLanguage(Enums.Language language) { this.language = language; }

    private void makeWorldFolder()
    {
        String basePath = System.getProperty("user.dir") + "\\saves\\" + GlobalSettings.worldName;
        File dir = null;
        try {
            dir = new File(basePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dir.mkdirs();
    }

    GlobalSettings()
    {
        setLanguage(Enums.Language.valueOf(System.getProperty("user.language").toUpperCase().split("-")[0]));
        dataLoader = new DataLoader(language);

        MAX_INITIATIVE = Integer.parseInt(API.dataLoaderAPI.getConfig("max_initiative", "config").get(0));
        CHUNK_SIZE = Integer.parseInt(API.dataLoaderAPI.getConfig("chunk_size", "config").get(0));
        RENDERING_DISTANCE = Integer.parseInt(API.dataLoaderAPI.getConfig("rendering_distance", "config").get(0));
    }
}
