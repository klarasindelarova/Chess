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
    private Piece[] copyOfPieces;
    private int indexOfClickedField;
    private int indexOfLastClickedField;
    private Label[] arrayOfLabels;
    private boolean isWhiteTurn;
    private MoveCallback moveCallback;
    private List<Integer> highlightedFields;
    private Logger logger = LogManager.getLogger(ChessEngine.class);
    private Text recordOfGame;
    private int numberOfRound;

    public ChessEngine() {
        this.pieces = new Piece[64];
        this.copyOfPieces = new Piece[64];
        this.isWhiteTurn = true;
        this.numberOfRound = 0;
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
                List<Integer> possibleFields = getPossibleMoves(this.pieces, indexOfClickedField);
                inspectIfMoveCausesCheck(this.pieces[indexOfClickedField], possibleFields);
                this.indexOfLastClickedField = indexOfClickedField;
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
            addRecordOfMoveToNotation();
            if (isCheckMate(this.pieces)) {
                eraseHighlightAndDisable(this.arrayOfLabels);
            } else {
                this.changeTurn();
                moveCallback.onMove();
                setFieldsActive(arrayOfLabels, isWhiteTurn);
            }
        }
    }

    public void setArrayOfLabels(Label[] arrayOfLabels) {
        this.arrayOfLabels = arrayOfLabels;
    }

    public void setIndexOfClickedField(int indexOfClickedField) {
        this.indexOfClickedField = indexOfClickedField;
    }

    public Text getRecordOfGame() {
        return this.recordOfGame;
    }

    public void setRecordOfGame(Text text) {
        this.recordOfGame = text;
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

    public String getOpponentPlayer() {
        if (isWhiteTurn) {
            return "BLACK";
        }
        return "WHITE";
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

    public boolean isPlayable(Piece[] pieces, int index) {
        return pieces[index] != null;
    }

    public void movePiece(Piece[] pieces, int currentIndex, int newIndex) {
        Piece piece = getPieceAtIndex(pieces, currentIndex);
        setPieceAtIndex(pieces, piece, newIndex);
        setPieceAtIndex(pieces, null, currentIndex);

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
            occupiedFields = getOpponentOccupiedFields(this.pieces, getOpponentPlayer());
        } else {
            occupiedFields = getOpponentOccupiedFields(this.pieces, getOpponentPlayer());
        }
        disableLabelsWithOpponentPieces(occupiedFields);

    }

    public List<Integer> getOpponentOccupiedFields(Piece[] pieces, String colour) {
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

    public void inspectIfMoveCausesCheck(Piece clickedPiece, List<Integer> possibleMovesOfClickedPiece) {
        setCopyOfPieces(pieces);
        List<Integer> possibleFutureMoves = new ArrayList<>();
        boolean isMoveOk = true;
        for (int move = 0; move < possibleMovesOfClickedPiece.size(); move++) {
            Piece formerPiece = this.copyOfPieces[possibleMovesOfClickedPiece.get(move)];
            this.copyOfPieces[indexOfClickedField] = null;
            this.copyOfPieces[possibleMovesOfClickedPiece.get(move)] = clickedPiece;
            if (clickedPiece.getName().equals("o")) {
                if (isKingInCheck(this.copyOfPieces, getCurrentPlayer(), getOpponentPlayer())) {
                    isMoveOk = false;
                } else {
                    isMoveOk = true;
                }
            } else {
                if (isKingInCheck(this.copyOfPieces, getCurrentPlayer(), getOpponentPlayer())) {
                    isMoveOk = false;
                }
            }
            if (isMoveOk) {
                possibleFutureMoves.add(possibleMovesOfClickedPiece.get(move));
            }
            this.copyOfPieces[indexOfClickedField] = clickedPiece;
            this.copyOfPieces[possibleMovesOfClickedPiece.get(move)] = formerPiece;
        }

        possibleMovesOfClickedPiece.clear();
        possibleMovesOfClickedPiece.addAll(possibleFutureMoves);
    }

    public boolean isKingInCheck(Piece[] pieces, String colour, String opponentColour) {
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

    public void addRecordOfMoveToNotation() {
        if (isWhiteTurn) {
            this.numberOfRound = numberOfRound + 1;
            if (isCheckMate(this.pieces)) {
                recordOfGame.setText(getRecordOfWhiteMove() + "#   ");
            } else if (isKingInCheck(this.pieces, getOpponentPlayer(), getCurrentPlayer())) {
                recordOfGame.setText(getRecordOfWhiteMove() + "+   ");
            } else {
                recordOfGame.setText(getRecordOfWhiteMove() + "   ");
            }
        } else {
            if (isCheckMate(this.pieces)) {
                recordOfGame.setText(getRecordOfBlackMove() + "# \r\n");
            } else if (isKingInCheck(this.pieces, getOpponentPlayer(), getCurrentPlayer())) {
                recordOfGame.setText(getRecordOfBlackMove() + "+ \r\n");
            } else {
                recordOfGame.setText(getRecordOfBlackMove() + "\r\n");
            }
        }

    }

    public String getRowFromIndex(Piece[] pieces, int index) {
        int row = index / 8;
        return switch (row) {
            case 0 -> "8";
            case 1 -> "7";
            case 2 -> "6";
            case 3 -> "5";
            case 4 -> "4";
            case 5 -> "3";
            case 6 -> "2";
            case 7 -> "1";
            default -> "Invalid row";
        };
    }

    public String getColumnFromIndex(Piece[] pieces, int index) {
        int column = index % 8;
        return switch (column) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "Invalid column";
        };
    }

    public String getRecordOfWhiteMove() {
        return recordOfGame.getText() + numberOfRound + ". " + "  " + getPieceAtIndex(this.pieces, indexOfClickedField).getNotation() +
                getColumnFromIndex(this.pieces, indexOfClickedField) + getRowFromIndex(this.pieces, indexOfClickedField);
    }

    public String getRecordOfBlackMove() {
        return recordOfGame.getText() + getPieceAtIndex(this.pieces, indexOfClickedField).getNotation() +
                getColumnFromIndex(this.pieces, indexOfClickedField) + getRowFromIndex(this.pieces, indexOfClickedField);
    }

    public boolean isCheckMate(Piece[] pieces) {
        setCopyOfPieces(pieces);
        List<Integer> opponentFields = getOpponentOccupiedFields(pieces, getOpponentPlayer());
        List<Integer> possibleMovesOfPiece;
        List<Integer> sumOfPossibleMoves = new ArrayList<>();
        boolean isMoveOk = true;
        for (int i = 0; i < opponentFields.size(); i++) {
            Piece chosenPiece = pieces[opponentFields.get(i)];
            possibleMovesOfPiece = getPossibleMoves(pieces, opponentFields.get(i));
            for (int move = 0; move < possibleMovesOfPiece.size(); move++) {
                Piece formerPiece = this.copyOfPieces[possibleMovesOfPiece.get(move)];
                this.copyOfPieces[opponentFields.get(i)] = null;
                this.copyOfPieces[possibleMovesOfPiece.get(move)] = chosenPiece;
                if (chosenPiece.getName().equals("o")) {
                    if (isKingInCheck(this.copyOfPieces, getOpponentPlayer(), getCurrentPlayer())) {
                        isMoveOk = false;
                    } else {
                        isMoveOk = true;
                    }
                } else {
                    if (isKingInCheck(this.copyOfPieces, getOpponentPlayer(), getCurrentPlayer())) {
                        isMoveOk = false;
                    }
                }
                if (isMoveOk) {
                    sumOfPossibleMoves.add(possibleMovesOfPiece.get(move));
                }
                this.copyOfPieces[opponentFields.get(i)] = chosenPiece;
                this.copyOfPieces[possibleMovesOfPiece.get(move)] = formerPiece;
            }
        }
        return sumOfPossibleMoves.isEmpty();
    }


}