package bin.world;

import bin.system.API;
import bin.system.chunkMap.Chunk;
import bin.world.organism.Human.Human;
import bin.world.organism.Organism;
import lib.Enums;
import lib.Pair;

public class WorldSPI { //API for access from internal classes
    public Pair<Integer,Integer> graveyard = new Pair<>(0,0);

    public Organism createOrganism(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords,
                                   Pair<Integer, Integer> sectorID)
    {
        return Organism.create(specimen,coords,sectorID);
    }

    public Organism createOrganism(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        return Organism.create(specimen,coords);
    }

    public void cleanCorpse(Pair<Integer,Integer> sectorID) {
        API.systemAPI.getWorld().cleanCorpse(sectorID);
    }

    public void makeOrganism(Enums.Species.AllSpecies specimen, Pair<Integer,Integer> coords) {
        API.systemAPI.getWorld().makeOrganism(specimen,coords);
    }

    public void setField(Pair<Integer,Integer> coords, Organism organism) {
        API.systemAPI.getWorld().setField(coords, organism);
    }

    public Organism getField(Pair<Integer,Integer> coords) {
        return API.systemAPI.getWorld().getField(coords);
    }

    public Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords,
                                                             Pair<Integer,Integer> lowBound, Pair<Integer,Integer> highBound) {
        return API.systemAPI.getWorld().getCoordsInDirection(dir, fromCoords, lowBound, highBound);
    }

    public Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords,
                                                             Pair<Integer,Integer> sectorID){
        return API.systemAPI.getWorld().getCoordsInDirection(dir,fromCoords,sectorID);
    }

    public Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords)
    {
        return API.systemAPI.getWorld().getCoordsInDirection(dir, fromCoords);
    }

    public void log(Organism organism, String message) {
        API.systemAPI.getWorld().log(organism, message);
    }

    public void log(String message) {
        API.systemAPI.getWorld().log(message);
    }

    public Chunk getSector(Pair<Integer,Integer> coords)
    {
        return API.worldAPI.getMap().getChunkByCoords(coords);
    }

    public Human getHuman()
    {
        return API.systemAPI.getWorld().getHuman();
    }
}
