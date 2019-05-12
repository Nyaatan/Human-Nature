package bin.world;

import bin.enums.Directions;
import bin.enums.Species;
import bin.enums.species.Animals;
import bin.system.DataLoader;
import bin.system.GlobalSettings;
import bin.system.Pair;
import bin.world.organism.Animal;
import bin.world.organism.Organism;
import bin.world.organism.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static bin.enums.Values.INITIATIVE;

public class World {
    private final int worldID;
    private ArrayList<String> log = new ArrayList<>();
    private final Pair<Integer,Integer> graveyard = new Pair<>(0,0); //not accessible field on map, stores dead organism
    private Pair<Integer,Integer> mapDimensions = new Pair<>(Integer.parseInt(DataLoader.getConfig("map_size", "config").get(0)),
            Integer.parseInt(Objects.requireNonNull(DataLoader.getConfig("map_size", "config")).get(1)));
    //the size of the map as maximum X and maximum Y
    private ArrayList<ArrayList<Organism>> organisms = new ArrayList<>(15); //contains all existing organisms in an initiative-sorted manner
    private Organism[][] map; //reference array placing organisms in a place corresponding their coordinates,

    public World() //generates map and starting organisms
    {
        for(int i=0;i<15;++i) organisms.add(new ArrayList<>());
        map = new Organism[mapDimensions.getX()][mapDimensions.getY()];
        for(int y=1;y<mapDimensions.getY();++y)
        {
            for(int x=1;x<mapDimensions.getX();++x)
            {
                Species species = Species.HUMAN; //get random non-human specimen
                while(species.equals(Species.HUMAN)) species = Species.values()[ThreadLocalRandom.current().nextInt(Species.values().length)];
                if(ThreadLocalRandom.current().nextInt(
                        mapDimensions.getX()*mapDimensions.getY()) < mapDimensions.getX()*mapDimensions.getY()*
                        Integer.parseInt(DataLoader.getBlockConfig(species.toString(), "species").get("spawn_rate").get(0))/100){
                    //chance of spawning: map_fields * specimen.spawn_rate/100

                    makeOrganism(species , new Pair<>(x,y)); //make random organism
                }
                else map[x][y] = null;
            }
        }
        GlobalSettings.addWorld(this);
        this.worldID = GlobalSettings.getWorldID(this);
    }

    Pair<Integer,Integer> getCoordsInDirection(Directions dir, Pair<Integer, Integer> fromCoords)
    {
        if (dir.equals(Directions.UP) && fromCoords.getY() <= mapDimensions.getY())
            return new Pair<>(fromCoords.getX(), fromCoords.getY() + 1);

        else if (dir.equals(Directions.DOWN) && fromCoords.getY() > 0)
            return new Pair<>(fromCoords.getX(), fromCoords.getY() - 1);

        else if (dir.equals(Directions.LEFT) && fromCoords.getX() > 0)
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY());

        else if (dir.equals(Directions.RIGHT) && fromCoords.getX() <= mapDimensions.getX())
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY());

        else if (dir.equals(Directions.UPLEFT) && fromCoords.getX() > 0 && fromCoords.getY() <= mapDimensions.getY())
            return new Pair<>(fromCoords.getX() - 1, fromCoords.getY() + 1);

        else if (dir.equals(Directions.DOWNRIGHT) && fromCoords.getX() <= mapDimensions.getX() && fromCoords.getY() > 0)
            return new Pair<>(fromCoords.getX() + 1, fromCoords.getY() - 1);

        else return new Pair<>(fromCoords.getX(),fromCoords.getY());
    } //returns correct pair of coordinates based on direction. Coordinates can't be outside the map

    Organism getField(Pair<Integer, Integer> coords) { return map[coords.getX()][coords.getY()]; } //returns reference to object at given coordinates

    void setField(Pair<Integer, Integer> coords, Organism organism) { map[coords.getX()][coords.getY()] = organism;}

    void makeOrganism(Species specimen, Pair<Integer, Integer> coords) //create new organism of given specimen at given coords
    {
        if(map[coords.getX()][coords.getY()] == null) {
            Organism newOrganism;

            //TODO if (specimen.equals(HUMAN)){}

            boolean isAnimal = false;

            Animals[] aniVal = Animals.values(); //get animal species

            for(Animals animal : aniVal){
                if(animal.toString().equals(specimen.toString())) { isAnimal = true; break; } //chech if given specimen is an animal
            }

            if(isAnimal) newOrganism = new Animal(this.worldID, specimen, coords);
            else newOrganism = new Plant(this.worldID, specimen, coords); //if not, it a plant

            map[coords.getX()][coords.getY()] = newOrganism;

            organisms.get(newOrganism.getValue(INITIATIVE)).add(newOrganism);
        }
    }

    void cleanCorpse() //delete dead organisms from organisms list
    {
        for(ArrayList<Organism> organismList: organisms) {
            organismList.remove(map[graveyard.getX()][graveyard.getY()]);
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

    Organism[][] getMap() {return map;} //returns map

    ArrayList<ArrayList<Organism>> getOrganisms() {return organisms;} //returns list of organisms

    HashMap<Species, Integer> getPopulation() //returns number of every existing specimen
    {
        HashMap<Species,Integer> result = new HashMap<>();
        for(ArrayList<Organism> organismList : organisms)
        {
            for(Organism organism : organismList)
            {
                //if result has the specimen, increment the count, else add specimen to result
                if (result.keySet().contains(organism.getSpecies())) { result.put(organism.getSpecies(), result.get(organism.getSpecies()) + 1); }
                else result.put(organism.getSpecies(), 1);
            }
        }
        return result;
    }
/*
    void makeTurn()
    {
        //TODO HUMAN MOVE



    }

    private void computeSector(Organism[][] sector)
    {

    }*/

    public int getWorldID() {return this.worldID;}
}
