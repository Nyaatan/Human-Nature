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
        
    }
}
