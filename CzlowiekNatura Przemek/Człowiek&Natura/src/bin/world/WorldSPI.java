package bin.world;

import bin.system.chunkMap.Chunk;
import bin.world.organism.Human.Human;
import bin.world.organism.Organism;
import lib.API;
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

    public void cleanCorpse(Organism organism) {
        API.systemAPI.getWorld().cleanCorpse(organism);
    }

    public Organism makeOrganism(Enums.Species.AllSpecies specimen, Pair<Integer,Integer> coords) {
        return API.systemAPI.getWorld().makeOrganism(specimen,coords);
    }

    public void setField(Pair<Integer,Integer> coords, Organism organism) {
        API.systemAPI.getWorld().setField(coords, organism);
    }

    public Organism getField(Pair<Integer,Integer> coords) {
        return API.systemAPI.getWorld().getField(coords);
    }

    public Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords) {
        return API.systemAPI.getWorld().getCoordsInDirection(dir, fromCoords);
    }

    public void log(Organism organism, String message) {
        API.systemAPI.getWorld().log(organism, message);
    }

    public void log(String message) {
        API.systemAPI.getWorld().log(message);
    }

    public void log(Organism organism, String message, Organism killer) {
        API.systemAPI.getWorld().log(organism,message,killer);
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
