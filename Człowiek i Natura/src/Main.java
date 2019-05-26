import bin.system.Commander;
import bin.system.GameSystem;
import bin.system.chunkMap.ChunkMap;
import lib.API;
import lib.Enums;

import static lib.Enums.Commands.Move.DOWN;

public class Main {

    public static void main(String[] args) {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Start();
        Commander com = new Commander();
        //System.out.println(dataLoader.getItemDescription(ItemName.WOOD).getY());
        ChunkMap map = API.worldAPI.getMap();
        //GlobalSettings.getWorld(0).makeTurn();
        System.out.println(API.worldAPI.getPopulation());

        API.worldSPI.getHuman().getBuffs().add(Enums.Buff.TIMBERMAN);
        //API.worldSPI.getHuman().getBuffs().add(Enums.Buff.HOGWEED_RESISTANT);
        for(int i=0;i<10;++i)
        {
            //TODO cos nie działa
            com.giveCommand(DOWN);
            //System.out.println(API.worldSPI.getHuman().getCoords());
            API.worldSPI.getHuman().takeCommand(com);
            //System.out.println(API.worldSPI.getHuman().getInventory());
            //System.out.println(API.worldAPI.getPopulation());
        }
    }
}
