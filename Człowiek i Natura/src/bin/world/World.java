package bin.world;

import bin.enums.Directions;
import bin.enums.Species;
import bin.enums.species.Animals;
import bin.system.DataLoader;
import bin.system.Pair;
import bin.world.organism.Animal;
import bin.world.organism.Organism;
import bin.world.organism.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import static bin.enums.Values.INITIATIVE;

public class World {
    public static DataLoader dataLoader = new DataLoader();
    public static final Pair<Integer,Integer> graveyard = new Pair<>(0,0);

    private static Pair<Integer,Integer> mapDimensions = new Pair<>(Integer.parseInt(dataLoader.getConfig("map_size").get(0)), Integer.parseInt(dataLoader.getConfig("map_size").get(1)));
    //the size of the map as maximum X and maximum Y

    private static ArrayList<ArrayList<Organism>> organisms = new ArrayList<>(15); //contains all existing organisms in an initiative-sorted manner
    private static Organism[][] map; //reference array placing organisms in a place corresponding their coordinates,

    public static Pair<Integer,Integer> getCoordsInDirection(Directions dir, Pair<Integer, Integer> fromCoords)
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

    public static Organism getField(Pair<Integer,Integer> coords) { return map[coords.getX()][coords.getY()]; } //returns reference to object at given coordinates

    public static void setField(Pair<Integer,Integer> coords, Organism organism) { map[coords.getX()][coords.getY()] = organism;}

    public static void makeOrganism(Species specimen, Pair <Integer,Integer> coords)
    {
        if(map[coords.getX()][coords.getY()] == null) {
            Organism newOrganism = null; //TODO FIX

            boolean isAnimal = false;

            Animals[] aniVal = Animals.values();

            for(Animals animal : aniVal){
                if(animal.toString() == specimen.toString()) { isAnimal = true; break; }
            }

            if(isAnimal) newOrganism = new Animal(specimen, coords);
            else newOrganism = new Plant(specimen, coords);
            map[coords.getX()][coords.getY()] = newOrganism;

            if (newOrganism != null) {
                organisms.get(newOrganism.getValue(INITIATIVE)).add(newOrganism);
            }
        }
    }

    public static void cleanCorpses()
    {
        Iterator orgIter = organisms.iterator();
        while(orgIter.hasNext())
        {
            ArrayList<Organism> orgList = (ArrayList<Organism>) orgIter.next();
            Iterator orgListIter = orgList.iterator();
            while(orgListIter.hasNext())
            {
                Organism organism = (Organism) orgListIter.next();
                if(organism.getCoords().equals(graveyard)) orgListIter.remove();
            }
        }
        System.gc();
    }

    public World() //TODO test
    {
        for(int i=0;i<15;++i) organisms.add(new ArrayList<>());
        map = new Organism[mapDimensions.getX()][mapDimensions.getY()];
        for(int y=1;y<mapDimensions.getY();++y)
        {
            for(int x=1;x<mapDimensions.getX();++x)
            {
                if(ThreadLocalRandom.current().nextInt(mapDimensions.getX()*mapDimensions.getY()) < mapDimensions.getX()*mapDimensions.getY()*Integer.parseInt(dataLoader.getConfig("spawn_rate").get(0))/100){
                    //chance of spawning: map_fields * config.spawn_rate%
                    Species species = Species.HUMAN;
                    while(species.equals(Species.HUMAN)) species = Species.values()[ThreadLocalRandom.current().nextInt(Species.values().length)];
                    makeOrganism(species , new Pair<>(x,y)); //make random organism
                }
                else map[x][y] = null;
            }
        }
    }

    public static Organism[][] getMap() {return map;}

    public static ArrayList<ArrayList<Organism>> getOrganisms() {return organisms;}

    public static HashMap<Species, Integer> getPoplation()
    {
        HashMap<Species,Integer> result = new HashMap<>();
        for(ArrayList<Organism> organismList : organisms)
        {
            for(Organism organism : organismList)
            {
                if (result.keySet().contains(organism.getSpecies())) { result.put(organism.getSpecies(), result.get(organism.getSpecies()) + 1); }
                else result.put(organism.getSpecies(), 1);
            }
        }
        return result;
    }
}
