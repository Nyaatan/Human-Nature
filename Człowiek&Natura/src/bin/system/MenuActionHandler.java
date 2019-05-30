package bin.system;

import bin.ui.WorldStarter;
import javafx.stage.Stage;
import lib.Enums;

import static lib.API.systemAPI;

public class MenuActionHandler {
    private GameSystem gameSystem;

    public MenuActionHandler(GameSystem gameSystem)
    {
        this.gameSystem = gameSystem;
    }

    public void execCommand(Enums.MenuOption option, Stage stage) throws Exception {
        stage.close();
        switch (option)
        {
            case NEWGAME:
                WorldStarter worldStarter = new WorldStarter();
                worldStarter.Start();
                break;

            case LOAD:
                break;

            case EXIT:
                if(systemAPI.getWorld()!= null) systemAPI.getWorld().save();
                gameSystem.Stop();
                break;

        }
    }
}
