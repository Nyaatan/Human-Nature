package bin.world;

import bin.system.Commander;
import bin.system.GlobalSettings;
import bin.system.TurnComputer.TurnComputer;
import bin.system.chunkMap.Chunk;
import bin.system.chunkMap.ChunkMap;
import bin.system.chunkMap.ChunkMapGen;
import bin.world.organism.Human.Human;
import bin.world.organism.Organism;
import lib.API;
import lib.CommandRefusedException;
import lib.Enums;
import lib.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static lib.API.systemAPI;

public class World implements Serializable {
    private String name;

    private Human human;

    private ArrayList<String> log = new ArrayList<>();

    private ChunkMap map;

    public World() //generates map and starting organisms
    {
        systemAPI.addWorld(this);
        this.name = GlobalSettings.worldName;
        this.map = new ChunkMapGen().generate();
        Pair <Integer,Integer> humanCoords;
        do{
            humanCoords = new Pair<>(ThreadLocalRandom.current().nextInt(systemAPI.CHUNK_SIZE),
                    ThreadLocalRandom.current().nextInt(systemAPI.CHUNK_SIZE));
        } while(this.map.getField(humanCoords)!=null);
        this.human = (Human) makeOrganism(Enums.Species.AllSpecies.HUMAN, humanCoords);
        this.save();
    }

    Pair<Integer,Integer> getCoordsInDirection(Enums.Directions dir, Pair<Integer, Integer> fromCoords)
    {
        if (dir.equals(Enums.Directions.UPRIGHT))
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY() + 1);

        else if (dir.equals(Enums.Directions.DOWNLEFT))
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY() - 1);

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
    //returns correct pair of coordinates based on direction.

    Organism getField(Pair<Integer, Integer> coords) { return map.getField(coords); } //returns object at given coordinates

    void setField(Pair<Integer, Integer> coords, Organism organism) { map.setField(coords,organism); }

    Organism makeOrganism(Enums.Species.AllSpecies specimen, Pair<Integer, Integer> coords)
    {
        Organism newOrganism = API.worldSPI.createOrganism(specimen, coords);
        this.map.setField(newOrganism.getCoords(), newOrganism);
        return newOrganism;
    }

    void cleanCorpse(Organism organism) //delete dead organisms from organisms list
    {
        map.getChunkByCoords(organism.getCoords()).getOrganisms().get(organism.getValue(Enums.Values.INITIATIVE)).remove(organism);
        System.gc();
    }

    void log(Organism organism, String message) {
        String logText = organism + message + "\n";
        log.add(logText);
    }

    void log(String message) {log.add(message);}

    void log(Organism organism, String message, Organism killer) {
        String logText = organism + message + killer + "\n";
        log.add(logText);
    }
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

    public void makeTurn(Commander commander) throws CommandRefusedException {
        this.human.takeCommand(commander);
        TurnComputer turnComputer = new TurnComputer(this.map);
        turnComputer.computeTurn();
    }

    Human getHuman()
    {
        return this.human;
    }

    public void save()
    {
        String path = System.getProperty("user.dir") + "\\saves\\" + name + "\\" + name;
        try {
            new File(path).createNewFile();
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Chunk getCenterChunk()
    {
        return map.getCenterChunk();
    }
}
