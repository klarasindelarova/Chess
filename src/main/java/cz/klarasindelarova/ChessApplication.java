package cz.klarasindelarova;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChessApplication extends Application {

    private final Logger logger = LogManager.getLogger(ChessApplication.class);

    public void start(Stage stage) {

        logger.info("Hello World");
        Text notation = new Text();
        Label currentPlayer = new Label("WHITE");
        BorderPane layout = new BorderPane();
        Label turnTitle = new Label("Turn: ");
        GridPane board = new GridPane();

        HBox horizontalLayout = new HBox();
        VBox rightTextFields = new VBox();

        VBox leftBar = new VBox();
        VBox rightBar = new VBox();
        Label smallSqTLeft = new Label();
        smallSqTLeft.setMinSize(17, 17);
        smallSqTLeft.setMaxSize(17, 17);
        smallSqTLeft.setBackground(new Background(new BackgroundFill(Color.web("#CD853F"), CornerRadii.EMPTY, Insets.EMPTY)));
        Label smallSqBLeft = new Label();
        smallSqBLeft.setMinSize(17, 17);
        smallSqBLeft.setMaxSize(17, 17);
        smallSqBLeft.setBackground(new Background(new BackgroundFill(Color.web("#CD853F"), CornerRadii.EMPTY, Insets.EMPTY)));
        Label smallSqTRight = new Label();
        smallSqTRight.setMinSize(17, 17);
        smallSqTRight.setMaxSize(17, 17);
        smallSqTRight.setBackground(new Background(new BackgroundFill(Color.web("#CD853F"), CornerRadii.EMPTY, Insets.EMPTY)));
        Label smallSqBRight = new Label();
        smallSqBRight.setMinSize(17, 17);
        smallSqBRight.setMaxSize(17, 17);
        smallSqBRight.setBackground(new Background(new BackgroundFill(Color.web("#CD853F"), CornerRadii.EMPTY, Insets.EMPTY)));
        Label spacing1 = new Label();
        spacing1.setMinSize(17, 17);
        spacing1.setMaxSize(17, 17);
        Label spacing2 = new Label();
        spacing2.setMinSize(1, 1);
        spacing2.setMaxSize(1, 1);

        leftBar.getChildren().add(smallSqTLeft);
        leftBar.getChildren().addAll(createBarWithNumbers());
        leftBar.getChildren().add(smallSqBLeft);

        rightBar.getChildren().add(smallSqTRight);
        rightBar.getChildren().addAll(createBarWithNumbers());
        rightBar.getChildren().add(smallSqBRight);

        HBox lettersTop = new HBox();
        HBox lettersBottom = new HBox();

        lettersTop.getChildren().addAll(createBarWithLetters());
        lettersBottom.getChildren().addAll(createBarWithLetters());

        VBox boardWithBars = new VBox();
        boardWithBars.getChildren().addAll(lettersTop, board, lettersBottom);

        rightTextFields.getChildren().addAll(spacing2, turnTitle, currentPlayer, notation);
        rightTextFields.setSpacing(20);
        horizontalLayout.getChildren().addAll(leftBar, boardWithBars, rightBar, spacing1, rightTextFields);

        MenuBar menu = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem saveGame = new MenuItem("Save");
        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setOnAction(e -> stage.close());
        fileMenu.getItems().addAll(newGame, saveGame, exitGame);
        Menu settingsMenu = new Menu("Settings");
        MenuItem preferences = new MenuItem("Options");
        settingsMenu.getItems().add(preferences);
        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About Chess");
        helpMenu.getItems().add(about);
        menu.getMenus().addAll(fileMenu, settingsMenu, helpMenu);

        layout.setTop(menu);
        layout.setCenter(horizontalLayout);
        Scene scene = new Scene(layout, 850, 619);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.setResizable(false);

        Label[] arrayOfLabels = createLabels();
        ChessEngine engine = new ChessEngine(notation, currentPlayer, arrayOfLabels);
        addLabelsToPane(board, arrayOfLabels, engine);
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

    public Label[] createBarWithNumbers() {
        Label[] numbers = new Label[8];
        for (int i = 0; i < 8; i++) {
            String num = String.valueOf(8 - i);
            Label number = new Label(num);
            number.setMinSize(17, 70);
            number.setMaxSize(17, 70);
            number.setBackground(new Background(new BackgroundFill(Color.web("#CD853F"), CornerRadii.EMPTY, Insets.EMPTY)));
            number.setAlignment(Pos.CENTER);
            numbers[i] = number;
        }
        return numbers;
    }

    public Label[] createBarWithLetters() {
        Label[] letters = new Label[8];
        letters[0] = new Label("A");
        letters[1] = new Label("B");
        letters[2] = new Label("C");
        letters[3] = new Label("D");
        letters[4] = new Label("E");
        letters[5] = new Label("F");
        letters[6] = new Label("G");
        letters[7] = new Label("H");

        for (Label letter : letters) {
            letter.setMinSize(70, 17);
            letter.setMaxSize(70, 17);
            letter.setBackground(new Background(new BackgroundFill(Color.web("#CD853F"), CornerRadii.EMPTY, Insets.EMPTY)));
            letter.setAlignment(Pos.CENTER);
        }
        return letters;
    }


}

