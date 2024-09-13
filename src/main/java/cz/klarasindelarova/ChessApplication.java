package cz.klarasindelarova;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChessApplication extends Application {

    public void start(Stage stage) {
        BorderPane layout = new BorderPane();
        VBox rightTextFields = new VBox();
        Label turn = new Label("Turn: ");
        TextArea moves = new TextArea();
        GridPane board = new GridPane();

        moves.appendText("vez na c3");
        createFields(board);
        rightTextFields.getChildren().addAll(turn, moves);
        rightTextFields.setSpacing(10);

        layout.setCenter(board);
        layout.setRight(rightTextFields);

        Scene scene = new Scene(layout);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(ChessApplication.class);
    }

    public void createFields(GridPane pane) {
        boolean isBlack = true;
        for (int row = 0; row < 8; row++) {
            isBlack = !isBlack;
            for (int column = 0; column < 8; column++) {
                Label field = new Label();
                field.setMinSize(70, 70);
                field.setMaxSize(70, 70);
                if (isBlack) {
                    field.setBackground(new Background(new BackgroundFill(Color.web("#008000"), CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    field.setBackground(new Background(new BackgroundFill(Color.web("#FFFFE0"), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                pane.add(field, column, row);
                isBlack = !isBlack;
            }
        }
    }
}

