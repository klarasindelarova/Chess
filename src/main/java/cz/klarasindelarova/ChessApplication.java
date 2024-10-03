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

    private ChessEngine engine;

    public void start(Stage stage) {
        BorderPane layout = new BorderPane();
        HBox horizontalLayout = new HBox();
        VBox rightTextFields = new VBox();
        Label turn = new Label("Turn: ");
        Text moves = new Text();
        GridPane board = new GridPane();
        Label[] fields = new Label[64];
        moves.setText("vez na c3");

        this.engine = new ChessEngine();
        engine.initialSetup();
        createFields(board, fields, engine);
        setPiecesToBoard(engine, fields);

        engine.setFieldOfLabels(fields);


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

    public void createFields(GridPane pane, Label[] fieldsArray, ChessEngine engine) {
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
                fieldsArray[getIndexFromRowAndColumn(row, column)] = field;
                OnClickHandler click = new OnClickHandler(engine, getIndexFromRowAndColumn(row, column));
                field.setOnMouseClicked(click);
                isBlack = !isBlack;
            }
        }
    }

    public void setPiecesToBoard(ChessEngine engine, Label[] fields) {
        for (int field = 0; field < 64; field++) {
            fields[field].setEffect(null);
        }
        for (int index = 0; index < fields.length; index++) {
            if (engine.getPieceAtIndex(index) == null) {
                fields[index].setText("");
            } else {
                fields[index].setText(engine.getPieceAtIndex(index).getName());

                if (engine.getPieceAtIndex(index).getColour().equals("black")) {
                    fields[index].setStyle("-fx-text-fill: #000000;");
                } else {
                    fields[index].setStyle("-fx-text-fill: #F5F5F5;");
                }
            }

        }
    }

    public int getIndexFromRowAndColumn(int row, int column) {
        return 8 * row + column;
    }


}

