package bin.world;

import bin.enums.Species;
import bin.system.GlobalSettings;
import bin.world.organism.Organism;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldAPI{
    public static ArrayList<String> getLog(int worldID) { return GlobalSettings.getWorld(worldID).getLog();}
    public static Organism[][] getMap(int worldID) {return GlobalSettings.getWorld(worldID).getMap();}
    public static ArrayList<ArrayList<Organism>> getOrganisms(int worldID) {return GlobalSettings.getWorld(worldID).getOrganisms();}
    public static HashMap<Species, Integer> getPopulation(int worldID) { return GlobalSettings.getWorld(worldID).getPopulation();}
}
