import bin.system.Commander;
import bin.system.GameSystem;
import lib.API;
import lib.CommandRefusedException;
import lib.Enums;

import static lib.Enums.Commands.Move.DOWNLEFT;
import static lib.Enums.ItemName.AXE;
import static lib.Enums.ItemName.BRANCH;

public class Main {

    public static void main(String[] args) throws CommandRefusedException {
        GameSystem gameSystem = new GameSystem();
        gameSystem.Start();
        gameSystem.menuActionHandler.execCommand(Enums.MenuOption.NEWGAME);
        Commander com = new Commander();
        System.out.println(API.worldAPI.getPopulation());
        //API.worldSPI.getHuman().getBuffs().add(Enums.Buff.TIMBERMAN);
        //API.worldSPI.getHuman().getBuffs().add(Enums.Buff.HOGWEED_RESISTANT);

        for(int i=0;i<100;++i)
        {
            com.giveCommand(DOWNLEFT);
            try
            {
                API.systemAPI.getWorld().makeTurn(com);
            } catch (CommandRefusedException e) {
                e.printStackTrace();
            }
            System.out.println(API.worldSPI.getHuman().getInventory());
            if(API.worldSPI.getHuman().getInventory().count(BRANCH)==4)
            {
                com.giveCommand(Enums.Commands.Craft.AXE);
                API.systemAPI.getWorld().makeTurn(com);
            }
            if(API.worldSPI.getHuman().getInventory().contains(AXE))
            {
                com.giveCommand(Enums.Commands.Use.AXE);
                API.systemAPI.getWorld().makeTurn(com);
                System.out.println(API.worldSPI.getHuman().getBuffs());
            }
            //System.out.println(API.worldAPI.getLog());
            System.out.println(API.worldAPI.getPopulation());

        }
    }
}
