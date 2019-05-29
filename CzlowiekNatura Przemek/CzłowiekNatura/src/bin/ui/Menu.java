package bin.ui;

import bin.system.GameSystem;
import bin.system.MenuActionHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lib.Enums;

public class Menu extends Application {
    private GameSystem gameSystem;

    public void setGameSystem(GameSystem gameSystem)
    {
        this.gameSystem = gameSystem;
    }

    public void Launch()
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle("Human&Nature");

        Pane root = new Pane();

        for(Enums.MenuOption option : Enums.MenuOption.values())
        {
            root.getChildren().add(makeButton(option, stage));
        }

        stage.setScene(new Scene(root , 600,600));
        stage.show();
    }

    private Button makeButton(Enums.MenuOption option, Stage stage) {
        Button newButton = new Button(option.toString());

        newButton.setMinSize(200,50);

        switch(option)
        {
            case NEWGAME:
                newButton.relocate(200, 150);
                break;

            case LOAD:
                newButton.relocate(200, 275);
                break;

            case EXIT:
                newButton.relocate(200,400);
                break;
        }

        newButton.setOnAction(event -> {
            MenuActionHandler actionHandler = new MenuActionHandler(gameSystem);
            try {
                actionHandler.execCommand(option,stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return newButton;
    }
}
