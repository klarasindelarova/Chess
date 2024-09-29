package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(String colour) {
        super(colour);
        super.name = "l";
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directions = {
                {0, -1},
                {0, +1},
                {-1, 0},
                {+1, 0},
                {-1, -1},
                {-1, +1},
                {+1, -1},
                {+1, +1}
        };

        for (int[] direction : directions) {
            int rowOfFutureMove = rowOfPiece + direction[0];
            int columnOfFutureMove = columnOfPiece + direction[1];

            if (isInBounds(rowOfFutureMove, columnOfFutureMove)) {
                checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
            }
        }
        return possibleMoves;
    }

    public boolean isInCheck() {
        return false;
    }


}
