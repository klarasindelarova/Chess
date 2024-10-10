package cz.klarasindelarova;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class OnClickHandler implements EventHandler<MouseEvent> {

    private ChessEngine engine;
    private int index;


    public OnClickHandler(ChessEngine engine, int index) {
        this.engine = engine;
        this.index = index;
    }

    @Override
    public void handle(MouseEvent event) {
        engine.setIndexOfClickedField(index);
        engine.inspectMove();

    }



}
