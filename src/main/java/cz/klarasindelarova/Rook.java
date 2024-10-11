package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(String colour) {
        super("t", colour, "R");
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, Piece[] pieces, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directions = {
                {0, +1},
                {0, -1},
                {+1, 0},
                {-1, 0}
        };

        for (int[] direction : directions) {
            int rowOfFutureMove = rowOfPiece;
            int columnOfFutureMove = columnOfPiece;
            while (true) {
                rowOfFutureMove = rowOfFutureMove + direction[0];
                columnOfFutureMove = columnOfFutureMove + direction[1];
                if (!(isInBounds(rowOfFutureMove, columnOfFutureMove))) {
                    break;
                }
                if (!(engine.isPlayable(pieces, getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                } else {
                    if (!(engine.getPieceAtIndex(pieces, getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)).getColour().equals(this.colour))) {
                        possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return possibleMoves;
    }

}
