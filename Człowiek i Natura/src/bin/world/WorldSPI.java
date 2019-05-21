package bin.world;

import bin.system.API;
import lib.Enums;
import lib.Pair;
import bin.system.chunkMap.Chunk;
import bin.world.organism.Organism;

public class WorldSPI {
    public Pair<Integer,Integer> graveyard = new Pair<>(0,0);

    public Organism createOrganism(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords,
                                   Pair<Integer, Integer> sectorID)
    {
        return Organism.create(worldID,specimen,coords,sectorID);
    }

    public Organism createOrganism(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        return Organism.create(worldID,specimen,coords);
    }

    public void cleanCorpse(int worldID, Pair<Integer,Integer> sectorID) {
        API.systemAPI.getWorld(worldID).cleanCorpse(sectorID);
    }

    public void makeOrganism(int worldID, Enums.Species.AllSpecies specimen, Pair<Integer,Integer> coords) {
        API.systemAPI.getWorld(worldID).makeOrganism(specimen,coords);
    }

    public void setField(int worldID, Pair<Integer,Integer> coords, Organism organism) {
        API.systemAPI.getWorld(worldID).setField(coords, organism);
    }

    public Organism getField(int worldID, Pair<Integer,Integer> coords) {
        return API.systemAPI.getWorld(worldID).getField(coords);
    }

    public Pair<Integer,Integer> getCoordsInDirection(int worldID, Enums.Directions dir, Pair<Integer, Integer> fromCoords,
                                                             Pair<Integer,Integer> lowBound, Pair<Integer,Integer> highBound) {
        return API.systemAPI.getWorld(worldID).getCoordsInDirection(dir, fromCoords, lowBound, highBound);
    }

    public Pair<Integer,Integer> getCoordsInDirection(int worldID, Enums.Directions dir, Pair<Integer, Integer> fromCoords,
                                                             Pair<Integer,Integer> sectorID){
        return API.systemAPI.getWorld(worldID).getCoordsInDirection(dir,fromCoords,sectorID);
    }

    public Pair<Integer,Integer> getCoordsInDirection(int worldID, Enums.Directions dir, Pair<Integer, Integer> fromCoords)
    {
        return API.systemAPI.getWorld(worldID).getCoordsInDirection(dir, fromCoords);
    }

    public void log(int worldID, Organism organism, String message) {
        API.systemAPI.getWorld(worldID).log(organism, message);
    }

    public void log(int worldID, String message) {
        API.systemAPI.getWorld(worldID).log(message);
    }

    public Chunk getSector(int worldID, Pair<Integer,Integer> coords)
    {
        return API.worldAPI.getMap(worldID).getSectorByCoords(coords);
    }
}
