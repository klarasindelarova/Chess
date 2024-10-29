package cz.klarasindelarova;

public class Knight extends LeapingPiece {

    public Knight(String colour) {
        super("m", colour, "N");
    }

//    @Override
//    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
//        List<Integer> possibleMoves = new ArrayList<>();
//        int rowOfPiece = index / 8;
//        int columnOfPiece = index % 8;
//        int[][] directions = {
//                {-2, -1},
//                {-2, +1},
//                {-1, +2},
//                {-1, -2},
//                {+1, +2},
//                {+1, -2},
//                {+2, -1},
//                {+2, +1}
//        };
//
//        for (int[] direction : directions) {
//            int rowOfFutureMove = rowOfPiece + direction[0];
//            int columnOfFutureMove = columnOfPiece + direction[1];
//
//            if (isInBounds(rowOfFutureMove, columnOfFutureMove)) {
//                possibleMoves.add(giveIndexOfFieldIfMoveIsOk(pieces, rowOfFutureMove, columnOfFutureMove));
//            }
//        }
//        return possibleMoves;
//    }

    @Override
    public int[][] giveDirectionsOfMove() {
        return new int[][]{
                {-2, -1},
                {-2, +1},
                {-1, +2},
                {-1, -2},
                {+1, +2},
                {+1, -2},
                {+2, -1},
                {+2, +1}
        };
    }

}
