package bin.system;

import bin.system.dataLoader.DataLoaderAPI;
import bin.world.WorldAPI;
import bin.world.WorldSPI;

public class API {
    public static WorldAPI worldAPI;
    public static WorldSPI worldSPI;
    public static SystemAPI systemAPI;
    public static DataLoaderAPI dataLoaderAPI;

    public API()
    {
        worldAPI = new WorldAPI();
        worldSPI = new WorldSPI();
        systemAPI = new SystemAPI();
        dataLoaderAPI = new DataLoaderAPI();

    }
}
