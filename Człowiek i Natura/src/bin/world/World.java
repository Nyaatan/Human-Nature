package bin.world;

import bin.system.Commander;
import bin.system.chunkMap.ChunkMap;
import bin.system.chunkMap.ChunkMapGen;
import bin.world.organism.Human.Human;
import bin.world.organism.Organism;
import lib.API;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static lib.API.systemAPI;

public class World {
    private Human human;

    private ArrayList<String> log = new ArrayList<>();

    private final Pair<Integer,Integer> graveyard = new Pair<>(0,0); //not accessible field on map, stores dead organism

    private ChunkMap map; //reference array placing organisms in a place corresponding their coordinates,

    public World() //generates map and starting organisms
    {
        systemAPI.addWorld(this);
        this.map = new ChunkMapGen().generate();
        Pair <Integer,Integer> humanCoords;
        do{
            humanCoords = new Pair<>(ThreadLocalRandom.current().nextInt(systemAPI.CHUNK_SIZE),
                    ThreadLocalRandom.current().nextInt(systemAPI.CHUNK_SIZE));
        } while(this.map.getField(humanCoords)!=null);
        this.human = (Human) makeOrganism(Enums.Species.AllSpecies.HUMAN, humanCoords);
    }

    Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords)
    {
        if (dir.equals(Enums.Directions.UP))
            return new Pair<>(fromCoords.getX(), fromCoords.getY() + 1);

        else if (dir.equals(Enums.Directions.DOWN))
            return new Pair<>(fromCoords.getX(), fromCoords.getY() - 1);

        else if (dir.equals(Enums.Directions.LEFT))
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY());

        else if (dir.equals(Enums.Directions.RIGHT))
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY());

        else if (dir.equals(Enums.Directions.UPLEFT))
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY() + 1);

        else if (dir.equals(Enums.Directions.DOWNRIGHT))
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY() - 1);

        else return new Pair<>(fromCoords.getX(),fromCoords.getY());
    }
    /*
    Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords,
                                               Pair<Integer, Integer> sectorID)
    {
        Chunk chunk = map.getChunkByID(sectorID);
        Pair<Integer,Integer> sectorStartCoords = new Pair<>(sectorID.getX()* systemAPI.CHUNK_SIZE,
                sectorID.getY()* systemAPI.CHUNK_SIZE);
        Pair<Integer,Integer> sectorEndCoords = new Pair<>(sectorStartCoords.getX()+ systemAPI.CHUNK_SIZE,
                sectorStartCoords.getY()+ systemAPI.CHUNK_SIZE);
        return getCoordsInDirection(dir, fromCoords, sectorStartCoords , sectorEndCoords);
    }

    Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords)
    {
        Pair<Integer,Integer> sectorStartCoords = new Pair<>(graveyard.getX(), graveyard.getY());
        Pair<Integer,Integer> sectorEndCoords = new Pair<>(systemAPI.CHUNK_SIZE, systemAPI.CHUNK_SIZE); //TODO COS GROŹNEGO
        return getCoordsInDirection(dir, fromCoords, sectorStartCoords , sectorEndCoords);
    }*/

    //returns correct pair of coordinates based on direction. Coordinates can't be outside the map

    Organism getField(Pair<Integer, Integer> coords) { return map.getField(coords); } //returns object at given coordinates

    void setField(Pair<Integer, Integer> coords, Organism organism) { map.setField(coords,organism); }

    Organism makeOrganism(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        Organism newOrganism = API.worldSPI.createOrganism(specimen, coords);
        this.map.setField(newOrganism.getCoords(), newOrganism);
        return newOrganism;
    }

    void cleanCorpse(Pair<Integer, Integer> sectorID) //delete dead organisms from organisms list
    {
        for(ArrayList<Organism> organismList: map.getChunkByID(sectorID).getOrganisms()) {
            organismList.remove(map.getField(graveyard));
        }
        System.gc();
    }

    void log(Organism organism, String message) {
        String logText = organism.getSpecies() +
                "(x: " + organism.getCoords().getX() +
                ", y: " + organism.getCoords().getY() +
                ", ID: " + organism.toString().split("@")[1] +
                ") " + message;
        log.add(logText);
    }

    void log(String message) {log.add(message);}

    ArrayList<String> getLog() {
        ArrayList<String> returnLog = new ArrayList<>(log);
        log.clear();
        return returnLog;
    }

    ChunkMap getMap() {return map;} //returns map

    HashMap<Enums.Species.AllSpecies, Integer> getPopulation() //returns number of every existing specimen
    {
        return this.map.getPopulation();
    }

    public void makeTurn(Commander commander)
    {
        this.human.takeCommand(commander);
        //TODO
        //TurnComputer turnComputer = new TurnComputer(this.map);
        //turnComputer.compute();
    }

    Human getHuman()
    {
        return this.human;
    }
}
