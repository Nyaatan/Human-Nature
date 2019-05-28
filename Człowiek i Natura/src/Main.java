import bin.system.Commander;
import bin.system.GameSystem;
import bin.system.chunkMap.ChunkMap;
import lib.API;
import lib.CommandRefusedException;
import lib.Enums;

import static lib.Enums.Commands.Move.DOWNLEFT;

public class Main {

    public static void main(String[] args) throws CommandRefusedException {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Start();
        gameSystem.menuActionHandler.execCommand(Enums.MenuOption.NEWGAME);
        Commander com = new Commander();
        ChunkMap map = API.worldAPI.getMap();
        System.out.println(API.worldAPI.getPopulation());
        API.worldSPI.getHuman().getBuffs().add(Enums.Buff.TIMBERMAN);
        //API.worldSPI.getHuman().getBuffs().add(Enums.Buff.HOGWEED_RESISTANT);

        com.giveCommand(DOWNLEFT);

        API.systemAPI.getWorld().makeTurn(com);

        System.out.println(API.worldAPI.getLog());
        System.out.println(API.worldAPI.getPopulation());

        for(int i=0;i<10;++i)
        {
            com.giveCommand(DOWNLEFT);
            API.worldSPI.getHuman().takeCommand(com);
        }

        API.worldAPI.getCenterChunk();
    }
}
