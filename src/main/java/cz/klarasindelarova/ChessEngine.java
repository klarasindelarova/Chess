package cz.klarasindelarova;

import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {

    private Piece[] fields;
    private int indexOfClickedField;
    private int indexOfLastClickedField;
    private Label[] fieldOfLabels;

    public ChessEngine() {
        this.fields = new Piece[64];
    }

    public void inspectMove() {
        if (fieldOfLabels[indexOfClickedField].getEffect() == null) {
            eraseHighlightAndDisable(fieldOfLabels);
            if (isPlayable(indexOfClickedField)) {
                this.indexOfLastClickedField = indexOfClickedField;
                List<Integer> possibleFields = getPossibleMoves(indexOfClickedField);
                highlightFields(possibleFields, fieldOfLabels);
                setFieldsActive(fieldOfLabels);

            } else {
                eraseHighlightAndDisable(fieldOfLabels);
                setFieldsActive(fieldOfLabels);
            }

        } else {
            eraseHighlightAndDisable(fieldOfLabels);
            movePiece(indexOfLastClickedField, indexOfClickedField);
            setPiecesToBoard(this, fieldOfLabels);
            setFieldsActive(fieldOfLabels);
        }

    }

    public void setFieldOfLabels(Label[] fieldOfLabels) {
        this.fieldOfLabels = fieldOfLabels;
    }

    public void setIndexOfClickedField(int indexOfClickedField) {
        this.indexOfClickedField = indexOfClickedField;
    }

    public Piece[] getAllFields() {
        return this.fields;
    }

    public Piece getPieceAtIndex(int index) {
        return this.fields[index];
    }

    public void setPieceAtIndex(Piece piece, int index) {
        this.fields[index] = piece;
    }

    public void initialSetup() {
        this.fields[0] = new Rook("black");
        this.fields[1] = new Knight("black");
        this.fields[2] = new Bishop("black");
        this.fields[3] = new Queen("black");
        this.fields[4] = new King("black");
        this.fields[5] = new Bishop("black");
        this.fields[6] = new Knight("black");
        this.fields[7] = new Rook("black");
        for (int b = 8; b < 16; b++) {
            this.fields[b] = new Pawn("black");
        }
        for (int w = 48; w < 56; w++) {
            this.fields[w] = new Pawn("white");
        }
        this.fields[56] = new Rook("white");
        this.fields[57] = new Knight("white");
        this.fields[58] = new Bishop("white");
        this.fields[59] = new Queen("white");
        this.fields[60] = new King("white");
        this.fields[61] = new Bishop("white");
        this.fields[62] = new Knight("white");
        this.fields[63] = new Rook("white");
    }

    public List<Integer> getPossibleMoves(int index) {
        Piece clickedPiece = getPieceAtIndex(index);
        if (clickedPiece == null) {
            return new ArrayList<>();
        }
        return clickedPiece.givePossibleMoves(this, index);
    }

    public boolean isPlayable(int index) {
        if (this.fields[index] == null) {
            return false;
        }
        return true;
    }

    public void movePiece(int currentIndex, int newIndex) {
        Piece piece = getPieceAtIndex(currentIndex);
        List<Integer> possibleMoves = getPossibleMoves(currentIndex);
        if (possibleMoves.contains(newIndex)) {
            setPieceAtIndex(piece, newIndex);
            setPieceAtIndex(null, currentIndex);
        }
    }

    public void highlightFields(List<Integer> fieldsToHighlight, Label[] fields) {
        for (int index = 0; index < fieldsToHighlight.size(); index++) {
            InnerShadow innerShadow = new InnerShadow();
            innerShadow.setOffsetX(0);
            innerShadow.setOffsetY(0);
            innerShadow.setRadius(15);
            innerShadow.setChoke(0);
            innerShadow.setColor(Color.GREEN);
            fields[fieldsToHighlight.get(index)].setEffect(innerShadow);
        }
    }

    public void setPiecesToBoard(ChessEngine engine, Label[] fields) {
        for (int field = 0; field < 64; field++) {
            fields[field].setEffect(null);
        }
        for (int index = 0; index < 64; index++) {
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

    public void eraseHighlightAndDisable(Label[] fields) {
        for (int field = 0; field < 64; field++) {
            fields[field].setEffect(null);
            fields[field].setDisable(true);
            fields[field].setOpacity(1);
        }
    }

    public void setFieldsActive(Label[] fields) {
        for (int field = 0; field < 64; field++) {
            fields[field].setDisable(false);
        }
    }

}
