package cz.klarasindelarova;

import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ChessApplication extends Application {

    public void start(Stage stage) {
        BorderPane layout = new BorderPane();
        VBox rightTextFields = new VBox();
        Label turn = new Label("Turn: ");
        TextArea moves = new TextArea();
        GridPane board = new GridPane();
        Label[][] fields = new Label[8][8];

        moves.appendText("vez na c3");
        createFields(board, fields);
        setPiecesToInitialPosition(fields);

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

    public void createFields(GridPane pane, Label[][] fieldsArray) {
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
                Font chessFont = Font.loadFont(getClass().getResourceAsStream("/fonts/chess-7.ttf"), 55);
                field.setFont(chessFont);
                field.setAlignment(Pos.CENTER);
                fieldsArray[row][column] = field;
                isBlack = !isBlack;
            }
        }
    }

    public void setPiecesToInitialPosition(Label[][] fields) {
        fields[0][0].setText("t");
        fields[0][1].setText("m");
        fields[0][2].setText("v");
        fields[0][3].setText("w");
        fields[0][4].setText("l");
        fields[0][5].setText("v");
        fields[0][6].setText("m");
        fields[0][7].setText("t");
        for (int i = 0; i < fields[1].length; i++) {
            fields[1][i].setText("o");
        }
        fields[7][0].setText("r");
        fields[7][1].setText("n");
        fields[7][2].setText("b");
        fields[7][3].setText("q");
        fields[7][4].setText("k");
        fields[7][5].setText("b");
        fields[7][6].setText("n");
        fields[7][7].setText("r");
        for (int i = 0; i < fields[6].length; i++) {
            fields[6][i].setText("p");
        }
    }

}

