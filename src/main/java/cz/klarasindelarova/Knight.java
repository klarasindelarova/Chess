package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(String colour) {
        super("m", colour, "N");
    }

    @Override
    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directions = {
                {-2, -1},
                {-2, +1},
                {-1, +2},
                {-1, -2},
                {+1, +2},
                {+1, -2},
                {+2, -1},
                {+2, +1}
        };

        for (int[] direction : directions) {
            int rowOfFutureMove = rowOfPiece + direction[0];
            int columnOfFutureMove = columnOfPiece + direction[1];

            if (isInBounds(rowOfFutureMove, columnOfFutureMove)) {
                checkFiguresAroundAndAddMovesToList(pieces, possibleMoves, rowOfFutureMove, columnOfFutureMove);
            }
        }
        return possibleMoves;
    }

}
