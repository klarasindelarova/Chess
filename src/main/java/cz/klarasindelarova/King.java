package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    private ChessEngine engine;

    public King(String colour) {
        super("l", colour, "K");
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, Piece[] pieces, int index) {
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
                checkFiguresAroundAndAddMovesToList(engine, pieces, possibleMoves, rowOfFutureMove, columnOfFutureMove);
            }
        }

        return possibleMoves;
    }

}
