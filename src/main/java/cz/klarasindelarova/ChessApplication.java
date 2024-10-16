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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChessApplication extends Application {

    private Logger logger = LogManager.getLogger(ChessApplication.class);

    public void start(Stage stage) {

        logger.info("Hello World");
        Text notation = new Text();
        Label currentPlayer = new Label("WHITE");
        BorderPane layout = new BorderPane();
        HBox horizontalLayout = new HBox();
        VBox rightTextFields = new VBox();
        Label turnTitle = new Label("Turn: ");
        GridPane board = new GridPane();
        rightTextFields.getChildren().addAll(turnTitle, currentPlayer, notation);
        rightTextFields.setSpacing(20);
        horizontalLayout.getChildren().addAll(board, rightTextFields);
        horizontalLayout.setSpacing(30);
        layout.setTop(horizontalLayout);
        Scene scene = new Scene(layout, 850, 560);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.setResizable(false);

        ChessEngine engine = new ChessEngine(notation, currentPlayer);
        Label[] arrayOfLabels = createLabels();
        addLabelsToPane(board, arrayOfLabels, engine);
        engine.setArrayOfLabels(arrayOfLabels);
        engine.initialSetup();

        stage.show();
    }

    public static void main(String[] args) {
        launch(ChessApplication.class);
    }

    public void addLabelsToPane(GridPane pane, Label[] arrayOfLabels, ChessEngine engine) {
        for (int indexOfLabel = 0; indexOfLabel < arrayOfLabels.length; indexOfLabel++) {
            Label label = arrayOfLabels[indexOfLabel];
            OnClickHandler click = new OnClickHandler(engine, indexOfLabel);
            label.setOnMouseClicked(click);
            pane.add(label, getColumnFromIndex(indexOfLabel), getRowFromIndex(indexOfLabel));
        }
    }

    public Label[] createLabels() {
        Label[] arrayOfLabels = new Label[64];
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
                Font chessFont = Font.loadFont(getClass().getResourceAsStream("/fonts/chess-7.ttf"), 55);
                field.setFont(chessFont);
                field.setAlignment(Pos.CENTER);
                arrayOfLabels[getIndexFromRowAndColumn(row, column)] = field;
                isBlack = !isBlack;
            }
        }
        return arrayOfLabels;
    }

    public int getIndexFromRowAndColumn(int row, int column) {
        return 8 * row + column;
    }

    public int getRowFromIndex(int index) {
        return index / 8;
    }

    public int getColumnFromIndex(int index) {
        return index % 8;
    }

}

