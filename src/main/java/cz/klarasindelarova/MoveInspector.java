package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class MoveInspector {

    private final Piece[] pieces;
    private final int indexOfClickedField;
    private final Colour currentPlayer;

    public MoveInspector(Piece[] piecesToInspect, int indexOfClickedField, Colour currentPlayer) {
        this.pieces = piecesToInspect;
        this.indexOfClickedField = indexOfClickedField;
        this.currentPlayer = currentPlayer;
    }

    public static boolean isPlayable(Piece[] pieces, int index) {
        return pieces[index] != null;
    }

    public List<Integer> getPossibleMoves() {
        Piece clickedPiece = this.pieces[this.indexOfClickedField];
        if (clickedPiece == null) {
            return new ArrayList<>();
        }
        return clickedPiece.givePossibleMoves(this.pieces, this.indexOfClickedField);
    }

    private Piece movePieceAndGiveMovedPiece(Piece[] pieces, int from, int to) {
        Piece piece = pieces[from];
        pieces[to] = piece;
        pieces[from] = null;
        return piece;
    }

    private Piece[] createCopyOfPieces() {
        Piece[] copyOfPieces = new Piece[64];
        for (int i = 0; i < 64; i++) {
            copyOfPieces[i] = this.pieces[i];
        }
        return copyOfPieces;
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

    public List<Integer> inspectIfMoveCausesCheckAndGivePossibleMoves(Piece clickedPiece, List<Integer> possibleMovesOfClickedPiece) {
        Piece[] copyOfBoard = createCopyOfPieces();
        List<Integer> possibleFutureMoves = new ArrayList<>();
        for (int i = 0; i < possibleMovesOfClickedPiece.size(); i++) {
            int indexOfTargetField = possibleMovesOfClickedPiece.get(i);
            Piece pieceAtTargetField = copyOfBoard[indexOfTargetField];
            movePieceAndGiveMovedPiece(copyOfBoard, indexOfClickedField, indexOfTargetField);
            MoveInspector inspector = new MoveInspector(copyOfBoard, indexOfTargetField, currentPlayer);
            if (!inspector.isKingInCheck()) {
                possibleFutureMoves.add(indexOfTargetField);
            }
            copyOfBoard[indexOfClickedField] = clickedPiece;
            copyOfBoard[indexOfTargetField] = pieceAtTargetField;
        }
        return possibleFutureMoves;
    }

    public boolean isKingInCheck() {
        for (int i = 0; i < 64; i++) {
            Piece inspectedPiece = this.pieces[i];
            if (inspectedPiece == null) {
                continue;
            }
            String opponentColour = currentPlayer.opposite().getCode();
            if (inspectedPiece.getColour().equals(opponentColour)) {
                MoveInspector inspector = new MoveInspector(pieces, i, currentPlayer);
                List<Integer> possibleMoves = inspector.getPossibleMoves();
                if (possibleMoves.contains(getCurrentIndexOfKing(pieces, currentPlayer.getCode()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckMate() {
        Piece[] copyOfBoard = createCopyOfPieces();
        List<Integer> opponentFields = getOpponentOccupiedFields(copyOfBoard, currentPlayer.getCode());
        List<Integer> possibleMovesOfPiece;
        List<Integer> sumOfPossibleMoves = new ArrayList<>();
        for (int i = 0; i < opponentFields.size(); i++) {
            Integer i2 = opponentFields.get(i);
            Piece chosenPiece = copyOfBoard[i2];
            MoveInspector inspector = new MoveInspector(copyOfBoard, i2, currentPlayer);
            possibleMovesOfPiece = inspector.getPossibleMoves();
            for (int move = 0; move < possibleMovesOfPiece.size(); move++) {
                Integer i1 = possibleMovesOfPiece.get(move);
                Piece formerPiece = copyOfBoard[i1];
                copyOfBoard[i2] = null;
                copyOfBoard[i1] = chosenPiece;
                MoveInspector inspector1 = new MoveInspector(copyOfBoard, i1, currentPlayer);
                if (!inspector1.isKingInCheck()) {
                    sumOfPossibleMoves.add(i1);
                }
                copyOfBoard[i2] = chosenPiece;
                copyOfBoard[i1] = formerPiece;
            }
        }
        return sumOfPossibleMoves.isEmpty();
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

}
