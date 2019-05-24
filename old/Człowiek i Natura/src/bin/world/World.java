package bin.world;

import bin.system.API;
import bin.system.Commander;
import bin.system.chunkMap.Chunk;
import bin.system.chunkMap.ChunkMap;
import bin.system.mapGen.MapGenerator;
import bin.world.organism.Human.Human;
import bin.world.organism.Organism;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    private Human human;

    private final int worldID;

    private ArrayList<String> log = new ArrayList<>();

    private final Pair<Integer,Integer> graveyard = new Pair<>(0,0); //not accessible field on map, stores dead organism

    private Pair<Integer,Integer> mapDimensions = new Pair<>(
            Integer.parseInt(API.dataLoaderAPI.getConfig("map_size", "config").get(0)),
            Integer.parseInt(Objects.requireNonNull(API.dataLoaderAPI.getConfig("map_size", "config")).get(1)));

    private Pair<Integer,Integer> sectorSize = new Pair<>(
            Integer.parseInt(API.dataLoaderAPI.getConfig("sector_size", "config").get(0)),
            Integer.parseInt(Objects.requireNonNull(API.dataLoaderAPI.getConfig("sector_size", "config")).get(1)));
    //the size of the map as maximum X and maximum Y

    private ChunkMap map; //reference array placing organisms in a place corresponding their coordinates,

    public World() //generates map and starting organisms
    {
        API.systemAPI.addWorld(this);
        this.worldID = API.systemAPI.getWorldID(this);
        MapGenerator mapGen = new MapGenerator(this.worldID, this.mapDimensions, this.sectorSize);
        this.map = mapGen.generate();
        Pair <Integer,Integer> humanCoords;
        do{
            humanCoords = new Pair<>(ThreadLocalRandom.current().nextInt(this.mapDimensions.getX()), ThreadLocalRandom.current().nextInt(this.mapDimensions.getY()));
            System.out.println(humanCoords);
        } while(this.map.getField(humanCoords)!=null);
        makeOrganism(Enums.Species.AllSpecies.HUMAN, humanCoords);
    }

    Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords,
                                               Pair<Integer, Integer> lowBound, Pair<Integer, Integer> highBound)
    {
        if (dir.equals(Enums.Directions.UP) && fromCoords.getY() <= highBound.getY())
            return new Pair<>(fromCoords.getX(), fromCoords.getY() + 1);

        else if (dir.equals(Enums.Directions.DOWN) && fromCoords.getY() > lowBound.getY())
            return new Pair<>(fromCoords.getX(), fromCoords.getY() - 1);

        else if (dir.equals(Enums.Directions.LEFT) && fromCoords.getX() > lowBound.getX())
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY());

        else if (dir.equals(Enums.Directions.RIGHT) && fromCoords.getX() <= highBound.getX())
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY());

        else if (dir.equals(Enums.Directions.UPLEFT) && fromCoords.getX() > lowBound.getX() && fromCoords.getY() <= highBound.getY())
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY() + 1);

        else if (dir.equals(Enums.Directions.DOWNRIGHT) && fromCoords.getX() <= highBound.getX() && fromCoords.getY() > lowBound.getY())
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY() - 1);

        else return new Pair<>(fromCoords.getX(),fromCoords.getY());
    }

    Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords,
                                               Pair<Integer, Integer> sectorID)
    {
        Chunk chunk = map.getSectorByID(sectorID);
        Pair<Integer,Integer> sectorStartCoords = new Pair<>(sectorID.getX()* chunk.getSize().getX(),
                sectorID.getY()* chunk.getSize().getY());
        Pair<Integer,Integer> sectorEndCoords = new Pair<>(sectorStartCoords.getX()+ chunk.getSize().getX(),
                sectorStartCoords.getY()+ chunk.getSize().getY());
        return getCoordsInDirection(dir, fromCoords, sectorStartCoords , sectorEndCoords);
    }

    Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords)
    {
        Pair<Integer,Integer> sectorStartCoords = new Pair<>(graveyard.getX(), graveyard.getY());
        Pair<Integer,Integer> sectorEndCoords = new Pair<>(mapDimensions.getX(),mapDimensions.getY());
        return getCoordsInDirection(dir, fromCoords, sectorStartCoords , sectorEndCoords);
    }

    //returns correct pair of coordinates based on direction. Coordinates can't be outside the map

    Organism getField(Pair<Integer, Integer> coords) { return map.getField(coords); } //returns object at given coordinates

    void setField(Pair<Integer, Integer> coords, Organism organism) { map.setField(coords,organism); }

    void makeOrganism(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords) //createOrganism new organism of given specimen at given coords
    {
        Organism newOrganism = API.worldSPI.createOrganism(this.worldID, specimen, coords);
        this.map.setField(newOrganism.getCoords(), newOrganism);
    }

    void cleanCorpse(Pair<Integer, Integer> sectorID) //delete dead organisms from organisms list
    {
        for(ArrayList<Organism> organismList: map.getSectorByID(sectorID).getOrganisms()) {
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

    public int getWorldID() {return this.worldID;}

    Human getHuman()
    {
        return this.human;
    }
}
