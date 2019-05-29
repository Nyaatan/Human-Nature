package bin.ui;

import bin.world.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lib.API;

public class WorldStarter extends Application {
    private UI ui;

    public void Start() throws Exception {
        start(new Stage());
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setTitle("Human&Nature");

        StackPane root = new StackPane();

        TextField textField = new TextField("Enter world name");
        textField.setOnAction(event -> {
            doAction(textField,stage);
        });

        root.getChildren().addAll(textField);

        stage.setScene(new Scene(root,400,50));

        stage.show();
    }

    private void doAction(TextField field, Stage stage)
    {
        API.systemAPI.setWorldName(field.getText());
        new World();
        stage.close();
        ui = new UI();
        ui.create();
    }
}
