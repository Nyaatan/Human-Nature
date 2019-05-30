package bin.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WorldLoader extends Application {

    public void Start() throws Exception {
        start(new Stage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
    }
}
