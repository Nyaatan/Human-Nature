package bin.system.chunkMap;

import bin.world.organism.Organism;
import lib.API;
import lib.Enums;
import lib.Pair;

import java.io.Serializable;
import java.util.ArrayList;

import static lib.Enums.Species.AllSpecies.HUMAN;

public class Chunk implements Serializable {
    private Organism[][] chunk;
    Pair<Integer,Integer> ID;
    private ArrayList<ArrayList<Organism>> organisms;

    public Chunk(Pair<Integer,Integer> ID)
    {
        chunk = new Organism[API.systemAPI.CHUNK_SIZE][API.systemAPI.CHUNK_SIZE];
        this.ID = ID;
        organisms = new ArrayList<>();
        for(int i=0;i< API.systemAPI.MAX_INITIATIVE;++i)
        {
            organisms.add(new ArrayList<>());
        }
    }

    public Chunk(Chunk chunk)
    {
        this.chunk = chunk.chunk;
        this.ID = chunk.ID;
        this.organisms = chunk.organisms;
    }

    public Organism get(int x, int y) {
        return chunk[x][y];
    }

    public Organism get(Pair<Integer,Integer> coords) {
        Pair<Integer,Integer> local = toLocalCoords(coords);
        return get(local.getX(),local.getY());
    }

    public void add(int x, int y, Organism organism)
    {
        if(organism!=null&&organism.getSpecies()!=HUMAN)
            organisms.get(organism.getValue(Enums.Values.INITIATIVE)).add(organism);
        else if(chunk[x][y]!=null) {
            organisms.get(chunk[x][y].getValue(Enums.Values.INITIATIVE)).remove(chunk[x][y]);
        }

        chunk[x][y] = organism;
    }

    public void add(Pair<Integer,Integer> coords, Organism organism)
    {
        add(coords.getX(),coords.getY(),organism);
    }

    public Pair<Integer,Integer> toGlobalCoords(Pair<Integer,Integer> localCoords)
    {
        return new Pair<>(this.ID.getX()*API.systemAPI.CHUNK_SIZE+localCoords.getX(), this.ID.getY()*API.systemAPI.CHUNK_SIZE+localCoords.getY());
    }

    public static Pair<Integer,Integer> toLocalCoords(Pair<Integer,Integer> globalCoords)
    {
        if(globalCoords.getY()<0 && globalCoords.getX()<0)
        return new Pair<>(15-Math.abs(globalCoords.getX()%API.systemAPI.CHUNK_SIZE), 15-Math.abs(globalCoords.getY()%API.systemAPI.CHUNK_SIZE));

        else if(globalCoords.getX()<0)
            return new Pair<>(15-Math.abs(globalCoords.getX()%API.systemAPI.CHUNK_SIZE), globalCoords.getY()%API.systemAPI.CHUNK_SIZE);

        else if(globalCoords.getY()<0)
            return new Pair<>(globalCoords.getX()%API.systemAPI.CHUNK_SIZE, 15 - Math.abs(globalCoords.getY()%API.systemAPI.CHUNK_SIZE));

        return new Pair<>(globalCoords.getX()%API.systemAPI.CHUNK_SIZE, globalCoords.getY()%API.systemAPI.CHUNK_SIZE);
    }

    public ArrayList<ArrayList<Organism>> getOrganisms()
    {
        return organisms;
    }

    public Pair<Integer,Integer> getID() { return this.ID; }

    public Organism[][] getMap() {
        return this.chunk;
    }

    public Chunk copy(Chunk chunk) {
        return new Chunk(chunk);
    }

    public void setOrganisms(ArrayList<ArrayList<Organism>> organisms) {
        this.organisms = organisms;
    }

    public void remove(Organism organism) {
        organisms.get(organism.getValue(Enums.Values.INITIATIVE)).remove(organism);
    }
}
