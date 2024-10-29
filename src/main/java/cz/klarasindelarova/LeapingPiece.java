package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public abstract class LeapingPiece extends Piece {

    public LeapingPiece(String name, String colour, String notation) {
        super(name, colour, notation);
    }

    @Override
    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directions = giveDirectionsOfMove();

        for (int[] direction : directions) {
            int rowOfFutureMove = rowOfPiece + direction[0];
            int columnOfFutureMove = columnOfPiece + direction[1];

            if (isInBounds(rowOfFutureMove, columnOfFutureMove) && giveIndexOfFieldIfMoveIsOk(pieces, rowOfFutureMove, columnOfFutureMove) != null) {
                possibleMoves.add(giveIndexOfFieldIfMoveIsOk(pieces, rowOfFutureMove, columnOfFutureMove));
            }
        }
        return possibleMoves;
    }

    public abstract int[][] giveDirectionsOfMove();

}
