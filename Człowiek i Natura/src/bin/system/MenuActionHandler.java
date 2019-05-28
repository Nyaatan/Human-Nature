package bin.system;

import bin.world.World;
import lib.Enums;

import static lib.API.systemAPI;

public class MenuActionHandler {
    private GameSystem gameSystem;

    public MenuActionHandler(GameSystem gameSystem)
    {
        this.gameSystem = gameSystem;
    }

    public void execCommand(Enums.MenuOption option)
    {
        switch (option)
        {
            case NEWGAME:
                new World();
                break;

            case LOAD:
                break;

            case EXIT:
                systemAPI.getWorld().save();
                gameSystem.Stop();
                break;

        }
    }
}
