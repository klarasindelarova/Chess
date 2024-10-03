package cz.klarasindelarova;


import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(String colour) {
        super(colour);
        super.name = "o";
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index / 8;
        int columnOfPiece = index % 8;
        int[][] directionsBlack = {
                {+1, 0},
                {+1, +1},
                {+1, -1}
        };
        int[][] directionsWhite = {
                {-1, 0},
                {-1, +1},
                {-1, -1}

        };

        int rowOfFutureMove = rowOfPiece;
        int columnOfFutureMove = columnOfPiece;


        if (this.colour.equals("black")) {
            if (rowOfPiece == 6) {     //figurka muze v pristim tahu ziskat jednu z vyhozenych figurek
                giveMovesForAnyPositionExceptFirstRow(engine, directionsBlack, rowOfPiece, columnOfPiece, rowOfFutureMove,
                        columnOfFutureMove, possibleMoves);
                // vratit zpet vyhozenou figurku
            } else if (rowOfPiece == 1) {      // figurka je v pocatecnim postaveni
                rowOfFutureMove = rowOfPiece + 1;
                columnOfFutureMove = columnOfPiece + 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
                columnOfFutureMove = columnOfPiece - 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }

                columnOfFutureMove = columnOfPiece;
                if (!(engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                    rowOfFutureMove = rowOfPiece + 2;
                    if (!(engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                        possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                    }
                }
            } else {                    // figurka je v jakekoli pozici krome pocatecni a na predposlednim radku
                giveMovesForAnyPositionExceptFirstRow(engine, directionsBlack, rowOfPiece, columnOfPiece,
                        rowOfFutureMove, columnOfFutureMove, possibleMoves);
            }

        } else {           // figurka je bila
            if (rowOfPiece == 1) {      //figurka muze v pristim tahu ziskat jednu z vyhozenych figurek
                giveMovesForAnyPositionExceptFirstRow(engine, directionsWhite, rowOfPiece, columnOfPiece, rowOfFutureMove,
                        columnOfFutureMove, possibleMoves);
                // vratit zpet vyhozenou figurku
            } else if (rowOfPiece == 6) {   // figurka je v pocatecnim postaveni
                rowOfFutureMove = rowOfPiece - 1;
                columnOfFutureMove = columnOfPiece + 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
                columnOfFutureMove = columnOfPiece - 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }

                columnOfFutureMove = columnOfPiece;
                if (!(engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                    rowOfFutureMove = rowOfPiece - 2;
                    if (!(engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                        possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                    }
                }
            } else {
                giveMovesForAnyPositionExceptFirstRow(engine, directionsWhite, rowOfPiece, columnOfPiece,
                        rowOfFutureMove, columnOfFutureMove, possibleMoves);
            }
        }
        return possibleMoves;
    }


    public boolean isPossibleToTakePieceInNextMove(ChessEngine engine, int rowOfFutureMove, int columnOfFutureMove) {
        if (isInBounds(rowOfFutureMove, columnOfFutureMove)) {
            if (engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove))) {
                if (!(engine.getPieceAtIndex(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)).getColour().equals(this.colour)) &&
                        columnOfFutureMove < 8) {
                    return true;
                }
            }
        }
        return false;
    }

    public void giveMovesForAnyPositionExceptFirstRow(ChessEngine engine, int[][] directions, int RoP, int CoP,
                                                      int rowOfFutureMove, int columnOfFutureMove, List<Integer> possMoves ) {
        boolean firstRound = true;
        for (int[] direction : directions) {
            rowOfFutureMove = RoP + direction[0];
            columnOfFutureMove = CoP + direction[1];
            if (firstRound) {
                if (isInBounds(rowOfFutureMove, columnOfFutureMove)) {
                    if (!(engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                        possMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                    }
                }
                firstRound = false;
            } else {
                if (isInBounds(rowOfFutureMove, columnOfFutureMove)) {
                    if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                        possMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                    }
                }

            }
        }
    }
}
