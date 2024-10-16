package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(String colour) {
        super("v", colour, "B");
    }

    @Override
    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directions = {
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
