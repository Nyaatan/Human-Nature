package bin.world;

import bin.system.chunkMap.Chunk;
import bin.system.chunkMap.ChunkMap;
import lib.API;
import lib.Enums;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldAPI { //API for access from external classes
    public ArrayList<String> getLog() { return API.systemAPI.getWorld().getLog();}
    public ChunkMap getMap() {return API.systemAPI.getWorld().getMap();}
    public HashMap<Enums.Species.AllSpecies, Integer> getPopulation() { return API.systemAPI.getWorld().getPopulation();}
    public Chunk getCenterChunk() { return API.systemAPI.getWorld().getCenterChunk();}
}
