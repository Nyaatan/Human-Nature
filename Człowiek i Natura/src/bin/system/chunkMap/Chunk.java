package bin.system.chunkMap;

import bin.system.API;
import bin.world.organism.Organism;
import lib.Enums;
import lib.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class Chunk implements Serializable {
    private Organism[][] chunk;
    Pair<Integer,Integer> ID;
    private ArrayList<ArrayList<Organism>> organisms;
    private ArrayList<Organism> visitors;

    public Chunk(Pair<Integer,Integer> ID)
    {
        visitors = new ArrayList<>();
        chunk = new Organism[API.systemAPI.CHUNK_SIZE][API.systemAPI.CHUNK_SIZE];
        this.ID = ID;
        organisms = new ArrayList<>();
        for(int i=0;i< API.systemAPI.MAX_INITIATIVE;++i)
        {
            organisms.add(new ArrayList<>());
        }
    }

    public Organism get(int x, int y) {
        return chunk[x][y];
    }

    public Organism get(Pair<Integer,Integer> coords) {
        return get(coords.getX(),coords.getY());
    }

    public void add(int x, int y, Organism organism)
    {
        chunk[x][y] = organism;
        if(organism!=null)
            organisms.get(organism.getValue(Enums.Values.INITIATIVE)).add(organism);
    }

    public void add(Pair<Integer,Integer> coords, Organism organism)
    {
        add(coords.getX(),coords.getX(),organism);
    }

    public Pair<Integer,Integer> toGlobalCoords(Pair<Integer,Integer> localCoords)
    {
        return new Pair<>(this.ID.getX()*API.systemAPI.CHUNK_SIZE+localCoords.getX(), this.ID.getY()*API.systemAPI.CHUNK_SIZE+localCoords.getY());
    }

    public static Pair<Integer,Integer> toLocalCoords(Pair<Integer,Integer> globalCoords)
    {
        return new Pair<>(globalCoords.getX()%API.systemAPI.CHUNK_SIZE, globalCoords.getY()%API.systemAPI.CHUNK_SIZE);
    }

    public ArrayList<ArrayList<Organism>> getOrganisms()
    {
        return organisms;
    }

    public Pair<Integer,Integer> getID() { return this.ID; }

    public void addVisitor(Organism organism) {
        this.visitors.add(organism);
    }
}
