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

        int rowOfFutureMove = 0;
        int columnOfFutureMove = 0;

        if (this.colour.equals("black")) {
            if (rowOfPiece == 6) {      //figurka muze v pristim tahu ziskat jednu z vyhozenych figurek
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
                rowOfFutureMove = rowOfPiece + 1;
                columnOfFutureMove = columnOfPiece + 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
                columnOfFutureMove = columnOfPiece - 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
                rowOfFutureMove = rowOfPiece + 1;
                columnOfFutureMove = columnOfPiece;
                if (!(engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)) && rowOfFutureMove < 7)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
            }
        } else {           // figurka je bila
            if (rowOfPiece == 1) {      //figurka muze v pristim tahu ziskat jednu z vyhozenych figurek
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
                rowOfFutureMove = rowOfPiece - 1;
                columnOfFutureMove = columnOfPiece + 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
                columnOfFutureMove = columnOfPiece - 1;
                if (isPossibleToTakePieceInNextMove(engine, rowOfFutureMove, columnOfFutureMove)) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
                rowOfFutureMove = rowOfPiece - 1;
                columnOfFutureMove = columnOfPiece;
                if (!(engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
                    possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
                }
            }
        }
        return possibleMoves;
    }

    public int getIndexFromRowAndColumn(int row, int column) {
        return 8 * row + column;
    }

    public boolean isPossibleToTakePieceInNextMove(ChessEngine engine, int rowOfFutureMove, int columnOfFutureMove) {
        if (engine.isPlayable(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove))) {
            if (!(engine.getPieceAtIndex(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)).getColour().equals(this.colour)) && columnOfFutureMove < 8) {
                return true;
            }
        }
        return false;
    }

}
