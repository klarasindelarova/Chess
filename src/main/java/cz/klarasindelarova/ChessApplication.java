package cz.klarasindelarova;

import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ChessApplication extends Application {

    public void start(Stage stage) {
        BorderPane layout = new BorderPane();
        HBox horizontalLayout = new HBox();
        VBox rightTextFields = new VBox();
        Label turn = new Label("Turn: ");
        Text moves = new Text();
        GridPane board = new GridPane();
        Label[] fields = new Label[64];
        ChessEngine engine = new ChessEngine();
        engine.initialSetup();

        moves.setText("vez na c3");
        createFields(board, fields);
        setPiecesToBoard(engine, fields);

        /* fields[1][0].setOnMouseClicked((event) -> {
            if (fields[1][0].getText().equals("o")) {
                if (fields[2][0].getText().equals("") && fields[3][0].getText().equals("")) {
                    InnerShadow innerShadow = new InnerShadow();
                    innerShadow.setOffsetX(0);
                    innerShadow.setOffsetY(0);
                    innerShadow.setRadius(15);
                    innerShadow.setChoke(0);
                    innerShadow.setColor(Color.GREEN);
                    fields[2][0].setEffect(innerShadow);
                    fields[3][0].setEffect(innerShadow);
                }
            }
        }); */


        rightTextFields.getChildren().addAll(turn, moves);
        rightTextFields.setSpacing(20);

        horizontalLayout.getChildren().addAll(board, rightTextFields);
        horizontalLayout.setSpacing(30);

        layout.setTop(horizontalLayout);

        Scene scene = new Scene(layout, 850, 560);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(ChessApplication.class);
    }

    public void createFields(GridPane pane, Label[] fieldsArray) {
        boolean isBlack = true;
        for (int row = 0; row < 8; row++) {
            isBlack = !isBlack;
            for (int column = 0; column < 8; column++) {
                Label field = new Label();
                field.setMinSize(70, 70);
                field.setMaxSize(70, 70);
                if (isBlack) {
                    field.setBackground(new Background(new BackgroundFill(Color.web("#8B4513"), CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    field.setBackground(new Background(new BackgroundFill(Color.web("#CD853F"), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                pane.add(field, column, row);
                Font chessFont = Font.loadFont(getClass().getResourceAsStream("/fonts/chess-7.ttf"), 55);
                field.setFont(chessFont);
                field.setAlignment(Pos.CENTER);
                fieldsArray[(8 * row) + column] = field;
                isBlack = !isBlack;
            }
        }
    }

    public void setPiecesToBoard(ChessEngine engine, Label[] fields) {
        for (int index = 0; index < fields.length; index++) {
            if (engine.getPieceAtIndex(index) == null) {
                continue;
            } else {
                fields[index].setText(engine.getPieceAtIndex(index).getName());
            }
            if (engine.getPieceAtIndex(index).getColour().equals("black")) {
                fields[index].setStyle("-fx-text-fill: #000000;");
            } else {
                fields[index].setStyle("-fx-text-fill: #F5F5F5;");
            }
        }

    }

}

