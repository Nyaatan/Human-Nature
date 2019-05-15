package bin.world;

import bin.enums.Directions;
import bin.enums.Species;
import bin.system.GlobalSettings;
import bin.system.OrganismCreator;
import bin.system.Pair;
import bin.system.dataLoader.DataLoaderAPI;
import bin.system.mapGen.MapGenerator;
import bin.system.sectorMap.Sector;
import bin.system.sectorMap.SectorMap;
import bin.world.organism.Organism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class World {
    private final int worldID;
    private ArrayList<String> log = new ArrayList<>();
    private final Pair<Integer,Integer> graveyard = new Pair<>(0,0); //not accessible field on map, stores dead organism
    private Pair<Integer,Integer> mapDimensions = new Pair<>(
            Integer.parseInt(DataLoaderAPI.getConfig("map_size", "config").get(0)),
            Integer.parseInt(Objects.requireNonNull(DataLoaderAPI.getConfig("map_size", "config")).get(1)));
    private Pair<Integer,Integer> sectorSize = new Pair<>(
            Integer.parseInt(DataLoaderAPI.getConfig("sector_size", "config").get(0)),
            Integer.parseInt(Objects.requireNonNull(DataLoaderAPI.getConfig("sector_size", "config")).get(1)));
    //the size of the map as maximum X and maximum Y
    private SectorMap map; //reference array placing organisms in a place corresponding their coordinates,

    public World() //generates map and starting organisms
    {
        GlobalSettings.addWorld(this);
        this.worldID = GlobalSettings.getWorldID(this);
        MapGenerator mapGen = new MapGenerator(this.worldID, this.mapDimensions, this.sectorSize);
        this.map = mapGen.generate();
    }

    Pair<Integer,Integer> getCoordsInDirection(Directions dir, Pair<Integer, Integer> fromCoords,
                                               Pair<Integer,Integer> lowBound, Pair<Integer,Integer> highBound)
    {
        if (dir.equals(Directions.UP) && fromCoords.getY() <= highBound.getY())
            return new Pair<>(fromCoords.getX(), fromCoords.getY() + 1);

        else if (dir.equals(Directions.DOWN) && fromCoords.getY() > lowBound.getY())
            return new Pair<>(fromCoords.getX(), fromCoords.getY() - 1);

        else if (dir.equals(Directions.LEFT) && fromCoords.getX() > lowBound.getX())
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY());

        else if (dir.equals(Directions.RIGHT) && fromCoords.getX() <= highBound.getX())
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY());

        else if (dir.equals(Directions.UPLEFT) && fromCoords.getX() > lowBound.getX() && fromCoords.getY() <= highBound.getY())
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY() + 1);

        else if (dir.equals(Directions.DOWNRIGHT) && fromCoords.getX() <= highBound.getX() && fromCoords.getY() > lowBound.getY())
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY() - 1);

        else return new Pair<>(fromCoords.getX(),fromCoords.getY());
    }

    Pair<Integer,Integer> getCoordsInDirection(Directions dir, Pair<Integer, Integer> fromCoords, Pair<Integer,Integer> sectorID)
    {
        Sector sector = map.getSectorByID(sectorID);
        Pair<Integer,Integer> sectorStartCoords = new Pair<>(sectorID.getX()*sector.getSize().getX(),
                sectorID.getY()*sector.getSize().getY());
        Pair<Integer,Integer> sectorEndCoords = new Pair<>(sectorStartCoords.getX()+sector.getSize().getX(),
                sectorStartCoords.getY()+sector.getSize().getY());
        return getCoordsInDirection(dir, fromCoords, sectorStartCoords , sectorEndCoords);
    }

    //returns correct pair of coordinates based on direction. Coordinates can't be outside the map

    Organism getField(Pair<Integer, Integer> coords) { return map.getField(coords); } //returns object at given coordinates

    void setField(Pair<Integer, Integer> coords, Organism organism) { map.setField(coords,organism);}

    void makeOrganism(Species specimen, Pair<Integer, Integer> coords) //createOrganism new organism of given specimen at given coords
    {
        Organism newOrganism = OrganismCreator.createOrganism(this.worldID, specimen, coords);
        this.map.setField(newOrganism.getCoords(), newOrganism);
    }

    void cleanCorpse(Pair<Integer,Integer> sectorID) //delete dead organisms from organisms list
    {
        for(ArrayList<Organism> organismList: map.getSectorByID(sectorID).getOrganisms()) {
            organismList.remove(map.getField(graveyard));
        }
        System.gc();
    }

    void log(Organism organism, String message) {
        String logText = organism.getSpecies() + "(x: " + organism.getCoords().getX() + ", y: " + organism.getCoords().getY() + ", ID: " + organism.toString().split("@")[1] + ") " + message;
        log.add(logText);
    }

    void log(String message) {log.add(message);}

    ArrayList<String> getLog() {
        ArrayList<String> returnLog = new ArrayList<>(log);
        log.clear();
        return returnLog;
    }

    SectorMap getMap() {return map;} //returns map

    HashMap<Species, Integer> getPopulation() //returns number of every existing specimen
    {
        HashMap<Species,Integer> result = new HashMap<>();
        ArrayList<ArrayList<ArrayList<Organism>>> organismsExtended = new ArrayList<>();
        for(Sector sector : map.values())
        {
            organismsExtended.add(sector.getOrganisms());
        }

        for(ArrayList<ArrayList<Organism>> organisms : organismsExtended) {
            for (ArrayList<Organism> organismList : organisms) {
                for (Organism organism : organismList) {
                    //if result has the specimen, increment the count, else add specimen to result
                    if (result.keySet().contains(organism.getSpecies())) {
                        result.put(organism.getSpecies(), result.get(organism.getSpecies()) + 1);
                    } else result.put(organism.getSpecies(), 1);
                }
            }
        }
        return result;
    }
/*
    void makeTurn()
    {
        //TODO TurnComputer



    }

    private void computeSector(Organism[][] sector)
    {

    }*/

    public int getWorldID() {return this.worldID;}
}
