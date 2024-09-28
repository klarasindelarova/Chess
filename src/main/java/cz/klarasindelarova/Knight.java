package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(String colour) {
        super(colour);
        super.name = "m";
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index/8;
        int columnOfPiece = index%8;

        int rowOfFutureMove = rowOfPiece-2;
        int columnOfFutureMove = columnOfPiece-1;
        if (rowOfFutureMove > -1 && columnOfFutureMove > -1) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        columnOfFutureMove = columnOfPiece+1;
        if (rowOfFutureMove > -1 && columnOfFutureMove < 8) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        rowOfFutureMove = rowOfPiece+2;
        if (rowOfFutureMove < 8 && columnOfFutureMove < 8) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        columnOfFutureMove = columnOfPiece-1;
        if (rowOfFutureMove < 8 && columnOfFutureMove > -1) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        rowOfFutureMove = rowOfPiece+1;
        columnOfFutureMove = columnOfPiece-2;
        if (rowOfFutureMove < 8 && columnOfFutureMove > -1) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        rowOfFutureMove = rowOfPiece-1;
        if (rowOfFutureMove > -1 && columnOfFutureMove > -1) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        columnOfFutureMove = columnOfPiece+2;
        if (rowOfFutureMove > -1 && columnOfFutureMove < 8) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        rowOfFutureMove = rowOfPiece+1;
        if (rowOfFutureMove < 8 && columnOfFutureMove < 8) {
            checkFiguresAroundAndAddMovesToList(engine, possibleMoves, rowOfFutureMove, columnOfFutureMove);
        }

        return possibleMoves;
    }

    public void checkFiguresAroundAndAddMovesToList(ChessEngine engine, List<Integer> possibleMoves, int rowOfFutureMove, int columnOfFutureMove) {
        if (!(engine.isPlayable(8*rowOfFutureMove + columnOfFutureMove))) {
            possibleMoves.add(8*rowOfFutureMove + columnOfFutureMove);
        } else {
            if (!(engine.getPieceAtIndex(8*rowOfFutureMove + columnOfFutureMove).getColour().equals(this.colour))) {
                possibleMoves.add(8*rowOfFutureMove + columnOfFutureMove);
            }
        }
    }
}
