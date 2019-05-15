package bin.world;

import bin.enums.Species;
import bin.system.GlobalSettings;
import bin.system.sectorMap.SectorMap;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldAPI{
    public static ArrayList<String> getLog(int worldID) { return GlobalSettings.getWorld(worldID).getLog();}
    public static SectorMap getMap(int worldID) {return GlobalSettings.getWorld(worldID).getMap();}
    public static HashMap<Species, Integer> getPopulation(int worldID) { return GlobalSettings.getWorld(worldID).getPopulation();}
}
