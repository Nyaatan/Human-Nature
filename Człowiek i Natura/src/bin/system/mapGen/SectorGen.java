package bin.system.mapGen;

import bin.enums.Species;
import bin.system.OrganismCreator;
import bin.system.Pair;
import bin.system.dataLoader.DataLoaderAPI;
import bin.system.sectorMap.Sector;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class SectorGen extends RecursiveTask<Sector> { //TODO nie działa
    @Override
    protected Sector compute() {
        Pair<Integer,Integer> sectorSize = this.sector.getSize();
        for(int i=0;i<sectorSize.getX();++i)
        {
            for(int j=0;j<sectorSize.getY();++j)
            {
                Species species = Species.HUMAN; //get random non-human specimen
                while(species.equals(Species.HUMAN)) species = Species.values()[ThreadLocalRandom.current().nextInt(Species.values().length)];
                Pair <Integer,Integer> mapSize = new Pair<>( Integer.parseInt(
                        DataLoaderAPI.getConfig("map_size", "config").get(0)),
                        Integer.parseInt(
                                DataLoaderAPI.getConfig("map_size", "config").get(1)));

                if(ThreadLocalRandom.current().nextInt(
                        mapSize.getX()*mapSize.getY() ) < (
                        Math.ceil(mapSize.getX()*mapSize.getY() *
                                Integer.parseInt(DataLoaderAPI.getBlockConfig(species.toString(),
                                "species").get("spawn_rate").get(0))/100) ) )
                {//chance of spawning: map_fields * specimen.spawn_rate/100

                    this.sector.addOrganism(OrganismCreator.createOrganismID(this.worldID, species ,
                            this.sector.toGlobalCoords(new Pair<>(i,j)), this.ID)); //make random organism
                }
                else this.sector.setFieldLocal(new Pair<>(i,j), null);
            }
        }
        return this.sector;
    }

    private Sector sector;
    private Pair<Integer,Integer> ID;
    private int worldID;

    public SectorGen(Sector sector, Pair<Integer,Integer> ID, int worldID)
    {
        this.ID = ID;
        this.sector = sector;
        this.worldID = worldID;
    }


    public Pair<Integer, Integer> getID() {
        return ID;
    }
}
