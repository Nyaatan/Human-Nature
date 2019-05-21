package bin.system.chunkMap;

import bin.system.API;
import bin.world.organism.Organism;
import lib.Enums;
import lib.Pair;

import java.util.ArrayList;

public class Chunk {
    private Organism[][] sector;

    private ArrayList<ArrayList<Organism>> visitors;

    private Pair<Integer,Integer> size;
    private Pair<Integer,Integer> ID;
    private ArrayList<ArrayList<Organism>> organisms;

    public Chunk(Pair <Integer,Integer> size, Pair<Integer,Integer> ID)
    {
        this.visitors = new ArrayList<>();
        this.organisms = new ArrayList<>();
        this.ID = ID;
        this.size = size;
        this.sector = new Organism[size.getX()][size.getY()];
        for(int i = 0; i< API.systemAPI.MAX_INITIATIVE(); ++i)
        {
            this.organisms.add(new ArrayList<>());
        }
    }

    public Chunk(int x, int y, Pair<Integer,Integer> ID)
    {
        this.visitors = new ArrayList<>();
        this.organisms = new ArrayList<>();
        for(int i = 0; i< API.systemAPI.MAX_INITIATIVE(); ++i)
        {
            this.organisms.add(new ArrayList<>());
        }
        this.ID = ID;
        this.size = new Pair<>(x,y);
        this.sector = new Organism[x][y];
    }

    public Chunk(Chunk chunk) {
        this.visitors = new ArrayList<>(chunk.visitors);
        this.ID = chunk.ID.copy();
        this.size = chunk.size.copy();
        this.sector = chunk.sector.clone();
    }

    public Pair<Integer,Integer> toLocalCoords(Pair<Integer,Integer> globalCoords)
    {
        return new Pair<>(Math.floorMod(globalCoords.getX(),size.getX()), Math.floorMod(globalCoords.getY(), size.getY()));
    }

    public Pair<Integer,Integer> toGlobalCoords(Pair<Integer,Integer> localCoords)
    {
        Pair<Integer,Integer> defaultSectorSize = Pair.makeIntPair(API.dataLoaderAPI.getConfig("sector_size", "config"));
        return new Pair<>(this.ID.getX()*defaultSectorSize.getX()+localCoords.getX(), this.ID.getY()*defaultSectorSize.getY());
    }

    public Organism getFieldLocal(Pair<Integer,Integer> localCoords)
    {
        return this.sector[(localCoords.getX())][localCoords.getY()];
    }

    public void setFieldLocal(Pair<Integer,Integer> localCoords, Organism object)
    {
         this.sector[localCoords.getX()][localCoords.getY()] = object;
    }

    public Organism getFieldGlobal(Pair<Integer,Integer> globalCoords)
    {
        return this.getFieldLocal(toLocalCoords(globalCoords));
    }

    public void setFieldGlobal(Pair<Integer,Integer> globalCoords, Organism object)
    {
        Pair<Integer,Integer> localCoords = toLocalCoords(globalCoords);
        this.setFieldLocal(localCoords, object);
    }

    public Pair<Integer, Integer> getID() {
        return this.ID;
    }
    public Pair<Integer, Integer> getSize() {
        return this.size;
    }

    public void addOrganism(Organism organism)
    {
        this.organisms.get(organism.getValue(Enums.Values.INITIATIVE)).add(organism);
        this.setFieldGlobal(organism.getCoords(), organism);
    }

    public void addVisitor(Organism organism)
    {
        this.visitors.get(organism.getValue(Enums.Values.INITIATIVE)).add(organism);
    }

    public ArrayList<ArrayList<Organism>> getOrganisms()
    {
        return organisms;
    }

}
