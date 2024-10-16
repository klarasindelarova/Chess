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
    private Label[] arrayOfLabels;
    private boolean isWhiteTurn;
    private List<Integer> highlightedFields;
    private Logger logger = LogManager.getLogger(ChessEngine.class);
    private Text recordOfGame;
    private Label currentPlayer;
    private ChessNotation notation;

    public ChessEngine(Text recordOfGameTextField, Label currentPlayer) {
        this.pieces = new Piece[64];
        this.isWhiteTurn = true;
        this.notation = new ChessNotation();
        this.recordOfGame = recordOfGameTextField;
        this.currentPlayer = currentPlayer;
    }

    public void initialSetup() {
        setPiecesToInitialPositions();
        setPiecesToBoard();
        setFieldsActive();
    }

    public void inspectMoveAtIndex(int index) {
        logger.debug("InspectMove method called, clicked index:{}, lastClickedIndex:{}", indexOfClickedField, indexOfLastClickedField);
        setIndexOfClickedField(index);
        if (this.arrayOfLabels[indexOfClickedField].getEffect() == null) {
            eraseHighlightAndDisable();
            if (isPlayable(this.pieces, indexOfClickedField)) {
                List<Integer> possibleFields = getPossibleMoves(this.pieces, indexOfClickedField);
                List<Integer> updatedPossibleFields = inspectIfMoveCausesCheckAndGivePossibleMoves(this.pieces[indexOfClickedField], possibleFields);
                this.indexOfLastClickedField = indexOfClickedField;
                highlightFields(updatedPossibleFields);
                highlightedFields = updatedPossibleFields;
                setFieldsActive();
                setHighlightedFieldsActive(highlightedFields);
            } else {
                setFieldsActive();
            }
        } else {
            eraseHighlightAndDisable();
            Piece movedPiece = movePieceAndGiveMovedPiece(this.pieces, indexOfLastClickedField, indexOfClickedField);
            setPiecesToBoard();
            notation.addRecordOfMoveToNotation(movedPiece, indexOfClickedField, isKingInCheck(this.pieces, getOpponentPlayer(), getCurrentPlayer()),
                    isCheckMate(), isWhiteTurn);
            recordOfGame.setText(notation.getRecordOfGame());
            if (!(isCheckMate())) {
                changeTurn();
                currentPlayer.setText(getCurrentPlayer());
                setFieldsActive();
            }
        }
    }

    public static boolean isPlayable(Piece[] pieces, int index) {
        return pieces[index] != null;
    }

    public void setArrayOfLabels(Label[] arrayOfLabels) {
        this.arrayOfLabels = arrayOfLabels;
    }

    public void setIndexOfClickedField(int indexOfClickedField) {
        this.indexOfClickedField = indexOfClickedField;
    }

    private Piece[] createCopyOfPieces() {
        Piece[] copyOfPieces = new Piece[64];
        for (int i = 0; i < 64; i++) {
            copyOfPieces[i] = this.pieces[i];
        }
        return copyOfPieces;
    }

    private String getCurrentPlayer() {
        if (isWhiteTurn) {
            return "WHITE";
        }
        return "BLACK";
    }

    private String getOpponentPlayer() {
        if (isWhiteTurn) {
            return "BLACK";
        }
        return "WHITE";
    }

    private void changeTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    private void setPiecesToInitialPositions() {
        this.pieces[0] = new Rook(getOpponentPlayer());
        this.pieces[1] = new Knight(getOpponentPlayer());
        this.pieces[2] = new Bishop(getOpponentPlayer());
        this.pieces[3] = new Queen(getOpponentPlayer());
        this.pieces[4] = new King(getOpponentPlayer());
        this.pieces[5] = new Bishop(getOpponentPlayer());
        this.pieces[6] = new Knight(getOpponentPlayer());
        this.pieces[7] = new Rook(getOpponentPlayer());
        for (int b = 8; b < 16; b++) {
            this.pieces[b] = new Pawn(getOpponentPlayer());
        }
        for (int w = 48; w < 56; w++) {
            this.pieces[w] = new Pawn(getCurrentPlayer());
        }
        this.pieces[56] = new Rook(getCurrentPlayer());
        this.pieces[57] = new Knight(getCurrentPlayer());
        this.pieces[58] = new Bishop(getCurrentPlayer());
        this.pieces[59] = new Queen(getCurrentPlayer());
        this.pieces[60] = new King(getCurrentPlayer());
        this.pieces[61] = new Bishop(getCurrentPlayer());
        this.pieces[62] = new Knight(getCurrentPlayer());
        this.pieces[63] = new Rook(getCurrentPlayer());
    }

    private List<Integer> getPossibleMoves(Piece[] pieces, int index) {
        Piece clickedPiece = pieces[index];
        if (clickedPiece == null) {
            return new ArrayList<>();
        }
        return clickedPiece.givePossibleMoves(pieces, index);
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
            if (isPlayable(pieces, i)) {
                if (pieces[i].getColour().equals(colour)) {
                    occupiedFields.add(i);
                }
            }
        }
        return occupiedFields;
    }

    private void disableLabelsWithOpponentPieces() {
        List<Integer> opponentOccupiedLabels = getOpponentOccupiedFields(this.pieces, getOpponentPlayer());
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

    private int getCurrentIndexOfKing(Piece[] pieces, String colour) {
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

    private List<Integer> inspectIfMoveCausesCheckAndGivePossibleMoves(Piece clickedPiece, List<Integer> possibleMovesOfClickedPiece) {
        Piece[] copyOfBoard = createCopyOfPieces();
        List<Integer> possibleFutureMoves = new ArrayList<>();
        for (int i = 0; i < possibleMovesOfClickedPiece.size(); i++) {
            int indexOfTargetField = possibleMovesOfClickedPiece.get(i);
            Piece pieceAtTargetField = copyOfBoard[indexOfTargetField];
            movePieceAndGiveMovedPiece(copyOfBoard, indexOfClickedField, indexOfTargetField);
            if (!isKingInCheck(copyOfBoard, getCurrentPlayer(), getOpponentPlayer())) {
                possibleFutureMoves.add(indexOfTargetField);
            }
            copyOfBoard[indexOfClickedField] = clickedPiece;
            copyOfBoard[indexOfTargetField] = pieceAtTargetField;
        }
        return possibleFutureMoves;
    }

    private boolean isKingInCheck(Piece[] pieces, String colour, String opponentColour) {
        for (int i = 0; i < 64; i++) {
            Piece inspectedPiece = pieces[i];
            if (inspectedPiece == null) {
                continue;
            }
            if (inspectedPiece.getColour().equals(opponentColour)) {
                List<Integer> possibleMoves = getPossibleMoves(pieces, i);
                if (possibleMoves.contains(getCurrentIndexOfKing(pieces, colour))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCheckMate() {
        Piece[] copyOfBoard = createCopyOfPieces();
        List<Integer> opponentFields = getOpponentOccupiedFields(copyOfBoard, getOpponentPlayer());
        List<Integer> possibleMovesOfPiece;
        List<Integer> sumOfPossibleMoves = new ArrayList<>();
        boolean isMoveOk = true;
        for (int i = 0; i < opponentFields.size(); i++) {
            Piece chosenPiece = copyOfBoard[opponentFields.get(i)];
            possibleMovesOfPiece = getPossibleMoves(copyOfBoard, opponentFields.get(i));
            for (int move = 0; move < possibleMovesOfPiece.size(); move++) {
                Piece formerPiece = copyOfBoard[possibleMovesOfPiece.get(move)];
                copyOfBoard[opponentFields.get(i)] = null;
                copyOfBoard[possibleMovesOfPiece.get(move)] = chosenPiece;
                if (isKingInCheck(copyOfBoard, getOpponentPlayer(), getCurrentPlayer())) {
                    isMoveOk = false;
                } else {
                    isMoveOk = true;
                }
                if (isMoveOk) {
                    sumOfPossibleMoves.add(possibleMovesOfPiece.get(move));
                }
                copyOfBoard[opponentFields.get(i)] = chosenPiece;
                copyOfBoard[possibleMovesOfPiece.get(move)] = formerPiece;
            }
        }
        return sumOfPossibleMoves.isEmpty();
    }


}