package bin.ui;

import bin.system.GlobalSettings;
import bin.world.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lib.API;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

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
        try {
            deleteWorldFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new World();
        stage.hide();
        ui = new UI();
        ui.create();
    }

    private static void deleteWorldFolder() throws IOException {
        Path path = Paths.get(System.getProperty("user.dir") + "\\saves\\" + GlobalSettings.worldName);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                        throws IOException {
                    Files.delete(file);
                    return CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                    return handleException(e);
                }

                private FileVisitResult handleException(final IOException e) {
                    e.printStackTrace(); // replace with more robust error handling
                    return TERMINATE;
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                        throws IOException {
                    if (e != null) return handleException(e);
                    Files.delete(dir);
                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
