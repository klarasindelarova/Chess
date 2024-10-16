package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(String colour) {
        super("w", colour, "Q");
    }

    @Override
    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directions = {
                {0, +1},
                {0, -1},
                {+1, 0},
                {-1, 0},
                {-1, +1},
                {-1, -1},
                {+1, +1},
                {+1, -1}
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
                if (!(ChessEngine.isPlayable(pieces, getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                } else {
                    if (!(pieces[getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)].getColour().equals(this.colour))) {
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

