package cz.klarasindelarova;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChessApplication extends Application {

    private ChessEngine engine;
    private Label currentPlayer;
    private Logger logger = LogManager.getLogger(ChessApplication.class);

    public void start(Stage stage) {

        logger.info("Hello World");

        this.engine = new ChessEngine();
        this.currentPlayer = new Label("WHITE");
        BorderPane layout = new BorderPane();
        HBox horizontalLayout = new HBox();
        VBox rightTextFields = new VBox();
        Label turnTitle = new Label("Turn: ");
        Text notation = new Text();
        engine.setRecordOfGame(notation);
        GridPane board = new GridPane();
        Label[] fields = new Label[64];

        engine.initialSetup();
        createFields(board, fields, engine);
        setPiecesToBoard(engine, fields);

        engine.setArrayOfLabels(fields);
        engine.setMoveCallback(() -> {
            Platform.runLater(() -> {
                currentPlayer.setText(engine.getCurrentPlayer());
            });
        });

        for (int i = 0; i < 16; i++) {
            fields[i].setEffect(null);
            fields[i].setDisable(true);
            fields[i].setOpacity(1);
        }


        rightTextFields.getChildren().addAll(turnTitle, this.currentPlayer, notation);
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
            if (engine.getPieceAtIndex(engine.getPieces(), index) == null) {
                fields[index].setText("");
            } else {
                fields[index].setText(engine.getPieceAtIndex(engine. getPieces(), index).getName());

                if (engine.getPieceAtIndex(engine.getPieces(), index).getColour().equals("BLACK")) {
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

