package bin.world;

import bin.system.API;
import lib.Enums;
import bin.system.chunkMap.ChunkMap;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldAPI { //API for access from external classes
    public ArrayList<String> getLog(int worldID) { return API.systemAPI.getWorld(worldID).getLog();}
    public ChunkMap getMap(int worldID) {return API.systemAPI.getWorld(worldID).getMap();}
    public HashMap<Enums.Species.AllSpecies, Integer> getPopulation(int worldID) { return API.systemAPI.getWorld(worldID).getPopulation();}
}
