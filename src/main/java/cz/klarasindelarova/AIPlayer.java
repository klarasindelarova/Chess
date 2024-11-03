package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AIPlayer {

    private Random random;
    private Piece[] pieces;

    public AIPlayer(Piece[] pieces) {
        this.random = new Random();
        this.pieces = pieces;
    }

    public void makeRandomMove(MoveInspector inspector) {
        HashMap<Integer, Piece> piecePositionMap = new HashMap<>();
        for (int i = 0; i < this.pieces.length; i++) {
            Piece piece = this.pieces[i];
            if (piece != null) {
                piecePositionMap.put(i, piece);
            }
        }

        HashMap<Integer, Piece> blackPieces = new HashMap<>();
        for (Integer position : piecePositionMap.keySet()) {
            Piece inspectedPiece = piecePositionMap.get(position);
            if (inspectedPiece.getColour().equals("BLACK")) {
                blackPieces.put(position, inspectedPiece);
            }
        }

        int indexOfRandomPiece;
        int positionInKeySet;
        int indexOfTargetField;
        Piece randomPiece;
        List<Integer> blackPiecesKeySet = new ArrayList<>(blackPieces.keySet());

        while (true) {
            positionInKeySet = random.nextInt(blackPiecesKeySet.size());
            indexOfRandomPiece = blackPiecesKeySet.get(positionInKeySet);
            randomPiece = blackPieces.get(indexOfRandomPiece);

            List<Integer> possibleMoves = randomPiece.givePossibleMoves(this.pieces, indexOfRandomPiece);
            List<Integer> actualPossibleMoves = inspector.inspectIfMoveCausesCheckAndGivePossibleMoves(randomPiece, possibleMoves);
            if (!actualPossibleMoves.isEmpty()) {
                indexOfTargetField = actualPossibleMoves.get(random.nextInt(actualPossibleMoves.size()));
                break;
            }
        }
        this.pieces[indexOfTargetField] = randomPiece;
        this.pieces[indexOfRandomPiece] = null;
    }

}
