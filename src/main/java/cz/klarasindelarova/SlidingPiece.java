package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public abstract class SlidingPiece extends Piece {

    public SlidingPiece(String name, String colour, String notation) {
        super(name, colour, notation);
    }

    @Override
    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directions = giveDirectionsOfMove();

        for (int[] direction : directions) {
            int rowOfFutureMove = rowOfPiece;
            int columnOfFutureMove = columnOfPiece;
            while (true) {
                rowOfFutureMove = rowOfFutureMove + direction[0];
                columnOfFutureMove = columnOfFutureMove + direction[1];
                if (!(isInBounds(rowOfFutureMove, columnOfFutureMove))) {
                    break;
                }
                int indexFromRowAndColumn = getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove);
                if (!(MoveInspector.isPlayable(pieces, indexFromRowAndColumn))) {
                    possibleMoves.add(indexFromRowAndColumn);
                } else {
                    if (!(pieces[indexFromRowAndColumn].getColour().equals(this.colour))) {
                        possibleMoves.add(indexFromRowAndColumn);
                    }
                    break;
                }
            }
        }
        return possibleMoves;
    }

    public abstract int[][] giveDirectionsOfMove();
}
