package bin.system;

import bin.enums.Species;
import bin.enums.species.Animals;
import bin.world.WorldAPI;
import bin.world.organism.Animal;
import bin.world.organism.Organism;
import bin.world.organism.Plant;

public class OrganismCreator {
    public static Organism createOrganismID(int worldID, Species specimen, Pair<Integer, Integer> coords, Pair<Integer, Integer> ID)
    {
        Organism newOrganism;

        //TODO if (specimen.equals(HUMAN)){}

        boolean isAnimal = false;

        Animals[] aniVal = Animals.values(); //get animal species

        for(Animals animal : aniVal){
            if(animal.toString().equals(specimen.toString())) { isAnimal = true; break; } //chech if given specimen is an animal
        }

        if(isAnimal) newOrganism = new Animal(worldID, specimen, coords, ID);
        else newOrganism = new Plant(worldID, specimen, coords, ID); //if not, it a plant

        return newOrganism;
    }

    public static Organism createOrganism(int worldID, Species specimen, Pair<Integer, Integer> coords)
    {
        return createOrganismID(worldID, specimen, coords, WorldAPI.getMap(worldID).getSectorByCoords(coords).getID());
    }
}
