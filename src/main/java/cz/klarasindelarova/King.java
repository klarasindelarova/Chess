package cz.klarasindelarova;

public class King extends LeapingPiece {

    public King(String colour) {
        super("l", colour, "K");
    }

    @Override
    public int[][] giveDirectionsOfMove() {
        return new int[][]{
                {0, -1},
                {0, +1},
                {-1, 0},
                {+1, 0},
                {-1, -1},
                {-1, +1},
                {+1, -1},
                {+1, +1}
        };
    }

//    @Override
//    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
//        List<Integer> possibleMoves = new ArrayList<>();
//        int rowOfPiece = index / 8;
//        int columnOfPiece = index % 8;
//        int[][] directions = {
//                {0, -1},
//                {0, +1},
//                {-1, 0},
//                {+1, 0},
//                {-1, -1},
//                {-1, +1},
//                {+1, -1},
//                {+1, +1}
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

}
