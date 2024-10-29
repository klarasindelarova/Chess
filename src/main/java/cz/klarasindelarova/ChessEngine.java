package cz.klarasindelarova;

import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {

    private Piece[] pieces;
    private int indexOfClickedField;
    private int indexOfLastClickedField;
    private final Label[] arrayOfLabels;
    private Colour currentPlayer;
    private final Logger logger = LogManager.getLogger(ChessEngine.class);
    private final Text recordOfGame;
    private final Label currentPlayerLabel;
    private ChessNotation notation;


    public ChessEngine(Text recordOfGameTextField, Label currentPlayerLabel, Label[] arrayOfLabels) {
        this.pieces = new Piece[64];
        this.currentPlayer = Colour.WHITE;
        this.notation = new ChessNotation();
        this.recordOfGame = recordOfGameTextField;
        this.currentPlayerLabel = currentPlayerLabel;
        this.arrayOfLabels = arrayOfLabels;
    }

    public void newRound() {
        initialSetup();
        this.currentPlayerLabel.setText(this.currentPlayer.getCode());
        this.notation = new ChessNotation();
        this.recordOfGame.setText(null);
    }

    public void initialSetup() {
        clearBoard();
        this.currentPlayer = Colour.WHITE;
        setPiecesToInitialPositions();
        setPiecesToBoard();
        setFieldsActive();
    }

    public void inspectMoveAtIndex(int index) {
        logger.debug("InspectMove method called, clicked index:{}, lastClickedIndex:{}", indexOfClickedField, indexOfLastClickedField);
        setIndexOfClickedField(index);
        if (this.arrayOfLabels[indexOfClickedField].getEffect() == null) {
            eraseHighlightAndDisable();
            if (MoveInspector.isPlayable(this.pieces, indexOfClickedField)) {
                MoveInspector inspector = new MoveInspector(this.pieces, this.indexOfClickedField, this.currentPlayer);
                List<Integer> possibleMoves = inspector.getPossibleMoves();
                List<Integer> finalPossibleMoves = inspector.inspectIfMoveCausesCheckAndGivePossibleMoves(this.pieces[indexOfClickedField], possibleMoves);
                this.indexOfLastClickedField = indexOfClickedField;
                highlightFields(finalPossibleMoves);
                setFieldsActive();
                setHighlightedFieldsActive(finalPossibleMoves);
            } else {
                setFieldsActive();
            }
        } else {
            eraseHighlightAndDisable();
            Piece movedPiece = movePieceAndGiveMovedPiece(this.pieces, indexOfLastClickedField, indexOfClickedField);
            setPiecesToBoard();
            MoveInspector inspector = new MoveInspector(this.pieces, indexOfClickedField, currentPlayer.opposite());
            notation.addRecordOfMoveToNotation(movedPiece, indexOfClickedField, inspector.isKingInCheck(),
                    inspector.isCheckMate(), this.currentPlayer);
            recordOfGame.setText(notation.getRecordOfGame());
            if (!(inspector.isCheckMate())) {
                changeTurn();
                currentPlayerLabel.setText(currentPlayer.getCode());
                setFieldsActive();
            }
        }
    }

    public void setIndexOfClickedField(int indexOfClickedField) {
        this.indexOfClickedField = indexOfClickedField;
    }

    private void changeTurn() {
        currentPlayer = currentPlayer.opposite();
    }

    private void clearBoard() {
        for (Label label : this.arrayOfLabels) {
            label.setText("");
        }
        this.pieces = new Piece[64];
    }

    private void setPiecesToInitialPositions() {
        this.pieces[0] = new Rook(currentPlayer.opposite().getCode());
        this.pieces[1] = new Knight(currentPlayer.opposite().getCode());
        this.pieces[2] = new Bishop(currentPlayer.opposite().getCode());
        this.pieces[3] = new Queen(currentPlayer.opposite().getCode());
        this.pieces[4] = new King(currentPlayer.opposite().getCode());
        this.pieces[5] = new Bishop(currentPlayer.opposite().getCode());
        this.pieces[6] = new Knight(currentPlayer.opposite().getCode());
        this.pieces[7] = new Rook(currentPlayer.opposite().getCode());
        for (int b = 8; b < 16; b++) {
            this.pieces[b] = new Pawn(currentPlayer.opposite().getCode());
        }
        for (int w = 48; w < 56; w++) {
            this.pieces[w] = new Pawn(currentPlayer.getCode());
        }
        this.pieces[56] = new Rook(currentPlayer.getCode());
        this.pieces[57] = new Knight(currentPlayer.getCode());
        this.pieces[58] = new Bishop(currentPlayer.getCode());
        this.pieces[59] = new Queen(currentPlayer.getCode());
        this.pieces[60] = new King(currentPlayer.getCode());
        this.pieces[61] = new Bishop(currentPlayer.getCode());
        this.pieces[62] = new Knight(currentPlayer.getCode());
        this.pieces[63] = new Rook(currentPlayer.getCode());
    }


    private Piece movePieceAndGiveMovedPiece(Piece[] pieces, int from, int to) {
        Piece piece = pieces[from];
        pieces[to] = piece;
        pieces[from] = null;
        return piece;
    }

    private void highlightFields(List<Integer> fieldsToHighlight) {
        for (Integer index : fieldsToHighlight) {
            InnerShadow innerShadow = new InnerShadow();
            innerShadow.setOffsetX(0);
            innerShadow.setOffsetY(0);
            innerShadow.setRadius(15);
            innerShadow.setChoke(0);
            innerShadow.setColor(Color.GREEN);
            this.arrayOfLabels[index].setEffect(innerShadow);
        }
    }

    private void setPiecesToBoard() {
        for (int index = 0; index < 64; index++) {
            Piece inspectedPiece = this.pieces[index];
            if (inspectedPiece == null) {
                this.arrayOfLabels[index].setText("");
            } else {
                this.arrayOfLabels[index].setText(inspectedPiece.getName());

                if (inspectedPiece.getColour().equals("BLACK")) {
                    this.arrayOfLabels[index].setStyle("-fx-text-fill: #000000;");
                } else {
                    this.arrayOfLabels[index].setStyle("-fx-text-fill: #F5F5F5;");
                }
            }
        }
    }

    private void eraseHighlightAndDisable() {
        for (Label label : this.arrayOfLabels) {
            label.setEffect(null);
            label.setDisable(true);
            label.setOpacity(1);
        }
    }

    private void setFieldsActive() {
        for (int field = 0; field < 64; field++) {
            this.arrayOfLabels[field].setDisable(false);
        }
        disableLabelsWithOpponentPieces();
    }

    private List<Integer> getOpponentOccupiedFields(Piece[] pieces, String colour) {
        List<Integer> occupiedFields = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (MoveInspector.isPlayable(pieces, i)) {
                if (pieces[i].getColour().equals(colour)) {
                    occupiedFields.add(i);
                }
            }
        }
        return occupiedFields;
    }

    private void disableLabelsWithOpponentPieces() {
        List<Integer> opponentOccupiedLabels = getOpponentOccupiedFields(this.pieces, currentPlayer.opposite().getCode());
        for (int i = 0; i < opponentOccupiedLabels.size(); i++) {
            arrayOfLabels[opponentOccupiedLabels.get(i)].setDisable(true);
            arrayOfLabels[opponentOccupiedLabels.get(i)].setOpacity(1);
        }
    }

    private void setHighlightedFieldsActive(List<Integer> highlightedFields) {
        for (int i = 0; i < highlightedFields.size(); i++) {
            arrayOfLabels[highlightedFields.get(i)].setDisable(false);
        }
    }

}