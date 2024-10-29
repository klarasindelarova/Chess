package cz.klarasindelarova;


public class Bishop extends SlidingPiece {

    public Bishop(String colour) {
        super("v", colour, "B");
    }

    @Override
    public int[][] giveDirectionsOfMove() {
        return new int[][]{
                {-1, +1},
                {-1, -1},
                {+1, +1},
                {+1, -1}
        };
    }

//    @Override
//    public List<Integer> givePossibleMoves(Piece[] pieces, int index) {
//        List<Integer> possibleMoves = new ArrayList<>();
//        int rowOfPiece = index / 8;
//        int columnOfPiece = index % 8;
//        int[][] directions = {
//                {-1, +1},
//                {-1, -1},
//                {+1, +1},
//                {+1, -1}
//        };
//
//        for (int[] direction : directions) {
//            int rowOfFutureMove = rowOfPiece;
//            int columnOfFutureMove = columnOfPiece;
//
//            while (true) {
//                rowOfFutureMove = rowOfFutureMove + direction[0];
//                columnOfFutureMove = columnOfFutureMove + direction[1];
//
//                if (!(isInBounds(rowOfFutureMove, columnOfFutureMove))) {
//                    break;
//                }
//                int indexFromRowAndColumn = getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove);
//                if (!(MoveInspector.isPlayable(pieces, indexFromRowAndColumn))) {
//                    possibleMoves.add(indexFromRowAndColumn);
//                } else {
//                    if (!(pieces[indexFromRowAndColumn].getColour().equals(this.colour))) {
//                        possibleMoves.add(indexFromRowAndColumn);
//                    }
//                    break;
//                }
//            }
//        }
//        return possibleMoves;
//    }

}
