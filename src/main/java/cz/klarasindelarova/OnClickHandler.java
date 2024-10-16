package cz.klarasindelarova;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class OnClickHandler implements EventHandler<MouseEvent> {

    private final ChessEngine engine;
    private final int index;

    public OnClickHandler(ChessEngine engine, int index) {
        this.engine = engine;
        this.index = index;
    }

    @Override
    public void handle(MouseEvent event) {
        engine.inspectMoveAtIndex(index);
    }



}
