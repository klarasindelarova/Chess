package cz.klarasindelarova;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) {
        Label label = new Label("Hello world!");
        BorderPane pane = new BorderPane(label);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(Main.class);
    }
}
