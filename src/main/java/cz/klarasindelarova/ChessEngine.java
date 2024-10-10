package cz.klarasindelarova;

import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {

    private Piece[] pieces;
    private Piece[] copyOfPieces;
    private int indexOfClickedField;
    private int indexOfLastClickedField;
    private Label[] arrayOfLabels;
    private boolean isWhiteTurn;
    private MoveCallback moveCallback;
    private List<Integer> highlightedFields;
    private Logger logger = LogManager.getLogger(ChessEngine.class);

    public ChessEngine() {
        this.pieces = new Piece[64];
        this.copyOfPieces = new Piece[64];
        this.isWhiteTurn = true;

    }

    public interface MoveCallback {
        void onMove();
    }

    public void setMoveCallback(MoveCallback moveCallback) {
        this.moveCallback = moveCallback;
    }

    public void inspectMove() {
        logger.debug("InspectMove method called, clicked index:{}, lastClickedIndex:{}", indexOfClickedField, indexOfLastClickedField);
        if (arrayOfLabels[indexOfClickedField].getEffect() == null) {
            eraseHighlightAndDisable(arrayOfLabels);
            if (isPlayable(this.pieces, indexOfClickedField)) {
                this.indexOfLastClickedField = indexOfClickedField;
                List<Integer> possibleFields = getPossibleMoves(this.pieces, indexOfClickedField);
                inspectIfMoveCausesCheck(possibleFields);
                highlightFields(possibleFields, arrayOfLabels);
                highlightedFields = possibleFields;
                moveCallback.onMove();
                setFieldsActive(arrayOfLabels, isWhiteTurn);
                setHighlightedFieldsActive(highlightedFields);

            } else {
                eraseHighlightAndDisable(arrayOfLabels);
                setFieldsActive(arrayOfLabels, isWhiteTurn);
            }

        } else {
            eraseHighlightAndDisable(arrayOfLabels);
            movePiece(this.pieces, indexOfLastClickedField, indexOfClickedField);
            setPiecesToBoard(this, arrayOfLabels);
            this.changeTurn();
            moveCallback.onMove();
            setFieldsActive(arrayOfLabels, isWhiteTurn);
        }

    }

    public void setArrayOfLabels(Label[] arrayOfLabels) {
        this.arrayOfLabels = arrayOfLabels;
    }

    public void setIndexOfClickedField(int indexOfClickedField) {
        this.indexOfClickedField = indexOfClickedField;
    }

    public Piece[] getPieces() {
        return this.pieces;
    }

    public Piece[] getCopyOfPieces() {
        return this.copyOfPieces;
    }

    public void setCopyOfPieces(Piece[] pieces) {
        for (int i = 0; i < 64; i++) {
            this.copyOfPieces[i] = pieces[i];
        }
    }

    public String getCurrentPlayer() {
        if (isWhiteTurn) {
            return "WHITE";
        }
        return "BLACK";
    }

    public void changeTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    public Piece getPieceAtIndex(Piece[] pieces, int index) {
        return pieces[index];
    }

    public void setPieceAtIndex(Piece[] pieces, Piece piece, int index) {
        pieces[index] = piece;
    }

    public void initialSetup() {
        this.pieces[0] = new Rook("BLACK");
        this.pieces[1] = new Knight("BLACK");
        this.pieces[2] = new Bishop("BLACK");
        this.pieces[3] = new Queen("BLACK");
        this.pieces[4] = new King("BLACK");
        this.pieces[5] = new Bishop("BLACK");
        this.pieces[6] = new Knight("BLACK");
        this.pieces[7] = new Rook("BLACK");
        for (int b = 8; b < 16; b++) {
            this.pieces[b] = new Pawn("BLACK");
        }
        for (int w = 48; w < 56; w++) {
            this.pieces[w] = new Pawn("WHITE");
        }
        this.pieces[56] = new Rook("WHITE");
        this.pieces[57] = new Knight("WHITE");
        this.pieces[58] = new Bishop("WHITE");
        this.pieces[59] = new Queen("WHITE");
        this.pieces[60] = new King("WHITE");
        this.pieces[61] = new Bishop("WHITE");
        this.pieces[62] = new Knight("WHITE");
        this.pieces[63] = new Rook("WHITE");
    }

    public List<Integer> getPossibleMoves(Piece[] pieces, int index) {
        Piece clickedPiece = pieces[index];
        if (clickedPiece == null) {
            return new ArrayList<>();
        }
        return clickedPiece.givePossibleMoves(this, pieces, index);
    }

 /*   public List<Integer> getPossibleMoves(int index) {
        Piece clickedPiece = this.pieces[index];
        if (clickedPiece == null) {
            return new ArrayList<>();
        }
        return clickedPiece.givePossibleMoves(this, index);
    }

    public boolean isPlayable(int index) {
        return this.pieces[index] != null;
    } */

    public boolean isPlayable(Piece[] pieces, int index) {
        return pieces[index] != null;
    }

    public void movePiece(Piece[] pieces, int currentIndex, int newIndex) {
        Piece piece = getPieceAtIndex(pieces, currentIndex);
        setPieceAtIndex(pieces, piece, newIndex);
        setPieceAtIndex(pieces,null, currentIndex);

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
            if (engine.getPieceAtIndex(this.pieces, index) == null) {
                fields[index].setText("");
            } else {
                fields[index].setText(engine.getPieceAtIndex(this.pieces, index).getName());

                if (engine.getPieceAtIndex(this.pieces, index).getColour().equals("BLACK")) {
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

    public void setFieldsActive(Label[] fields, boolean isWhiteTurn) {
        for (int field = 0; field < 64; field++) {
            fields[field].setDisable(false);
        }
        List<Integer> occupiedFields;

        if (isWhiteTurn) {
            occupiedFields = getOpponentOccupiedFields("BLACK");
        } else {
            occupiedFields = getOpponentOccupiedFields("WHITE");
        }
        disableLabelsWithOpponentPieces(occupiedFields);

    }

    public List<Integer> getOpponentOccupiedFields(String colour) {
        List<Integer> occupiedFields = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (isPlayable(this.pieces, i)) {
                if (this.pieces[i].getColour().equals(colour)) {
                    occupiedFields.add(i);
                }
            }
        }
        return occupiedFields;
    }


    public void disableLabelsWithOpponentPieces(List<Integer> occupiedFields) {
        for (int i = 0; i < occupiedFields.size(); i++) {
            arrayOfLabels[occupiedFields.get(i)].setDisable(true);
            arrayOfLabels[occupiedFields.get(i)].setOpacity(1);
        }
    }

    public void setHighlightedFieldsActive(List<Integer> highlightedFields) {
        for (int i = 0; i < highlightedFields.size(); i++) {
            arrayOfLabels[highlightedFields.get(i)].setDisable(false);
        }
    }

    public int getCurrentIndexOfKing(Piece[] pieces, String colour) {
        for (int index = 0; index < 64; index++) {
            Piece inspectedPiece = pieces[index];
            if (inspectedPiece == null) {
                continue;
            }
            if (inspectedPiece.getColour().equals(colour) && inspectedPiece.getName().equals("l")) {
                return index;
            }
        }
        throw new IllegalStateException("No field with king found.");
    }

    public void inspectIfMoveCausesCheck(List<Integer> possibleMovesOfClickedPiece) {
        setCopyOfPieces(pieces);
        Piece clickedPiece = this.copyOfPieces[indexOfClickedField];
        List<Integer> possibleFutureMoves = new ArrayList<>();
        boolean isMoveOk = true;
        if (isWhiteTurn) {
            for (int move = 0; move < possibleMovesOfClickedPiece.size(); move++) {
                Piece formerPiece = this.copyOfPieces[possibleMovesOfClickedPiece.get(move)];
                this.copyOfPieces[indexOfClickedField] = null;
                this.copyOfPieces[possibleMovesOfClickedPiece.get(move)] = clickedPiece;
                for (int i = 0; i < 64; i++) {
                    Piece inspectedPiece = this.copyOfPieces[i];
                    if (inspectedPiece == null) {
                        continue;
                    }
                    if (inspectedPiece.getColour().equals("BLACK")) {
                        List<Integer> possibleMoves = getPossibleMoves(this.copyOfPieces, i);
                        if (possibleMoves.contains(getCurrentIndexOfKing(this.copyOfPieces, "WHITE"))) {
                            isMoveOk = false;
                            break;
                        }
                    }
                }
                if (isMoveOk) {
                    possibleFutureMoves.add(possibleMovesOfClickedPiece.get(move));
                }
                this.copyOfPieces[indexOfClickedField] = clickedPiece;
                this.copyOfPieces[possibleMovesOfClickedPiece.get(move)] = formerPiece;
            }
        } else {
            for (int move = 0; move < possibleMovesOfClickedPiece.size(); move++) {
                Piece formerPiece = this.copyOfPieces[possibleMovesOfClickedPiece.get(move)];
                this.copyOfPieces[indexOfClickedField] = null;
                this.copyOfPieces[possibleMovesOfClickedPiece.get(move)] = clickedPiece;
                for (int i = 0; i < 64; i++) {
                    Piece inspectedPiece = this.copyOfPieces[i];
                    if (inspectedPiece == null) {
                        continue;
                    }
                    if (inspectedPiece.getColour().equals("WHITE")) {
                        List<Integer> possibleMoves = getPossibleMoves(this.copyOfPieces, i);
                        if (possibleMoves.contains(getCurrentIndexOfKing(this.copyOfPieces, "BLACK"))) {
                            isMoveOk = false;
                            break;
                        }
                    }
                }
                if (isMoveOk) {
                    possibleFutureMoves.add(possibleMovesOfClickedPiece.get(move));
                }
                this.copyOfPieces[indexOfClickedField] = clickedPiece;
                this.copyOfPieces[possibleMovesOfClickedPiece.get(move)] = formerPiece;
            }
        }
        possibleMovesOfClickedPiece.clear();
        possibleMovesOfClickedPiece.addAll(possibleFutureMoves);
    }


}
