import bin.system.Commander;
import bin.system.GameSystem;
import bin.system.chunkMap.ChunkMap;
import bin.world.organism.Organism;
import lib.API;
import lib.CommandRefusedException;
import lib.Enums;
import lib.Pair;

import static lib.Enums.Commands.Move.DOWNLEFT;

public class Main {

    public static void main(String[] args) throws CommandRefusedException {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Start();
        Commander com = new Commander();
        //System.out.println(dataLoader.getItemDescription(ItemName.WOOD).getY());
        ChunkMap map = API.worldAPI.getMap();
        //GlobalSettings.getWorld(0).makeTurn();
        System.out.println(API.worldAPI.getPopulation());

        for(Organism[] row : map.getChunkByID(new Pair<>(0,0)).getMap())
        {
            for(Organism org : row) if(org!=null&&org.getSpecies()!= Enums.Species.AllSpecies.HUMAN) org.move();
        }

        API.worldSPI.getHuman().getBuffs().add(Enums.Buff.TIMBERMAN);
        //API.worldSPI.getHuman().getBuffs().add(Enums.Buff.HOGWEED_RESISTANT);

        System.out.println(API.worldAPI.getLog());


        System.out.println(API.worldAPI.getPopulation());
        for(int i=0;i<10;++i)
        {
            com.giveCommand(DOWNLEFT);
            //System.out.println(API.worldSPI.getHuman().getCoords());
            API.worldSPI.getHuman().takeCommand(com);
            //System.out.println(API.worldSPI.getHuman().getInventory());
            //System.out.println(API.worldAPI.getPopulation());
        }
    }
}
