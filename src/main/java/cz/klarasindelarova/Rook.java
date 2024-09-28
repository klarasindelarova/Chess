package cz.klarasindelarova;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{

    public Rook(String colour) {
        super(colour);
        super.name = "t";
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, int index) {
        List<Integer> possibleMoves = new ArrayList<>();
        int rowOfPiece = index/8;
        int columnOfPiece = index%8;

        for (int row = rowOfPiece+1; row < 8; row++) {
            if (!(engine.isPlayable((row*8)+columnOfPiece))) {
                possibleMoves.add((row*8)+columnOfPiece);
            } else {
                if (engine.getPieceAtIndex((row*8)+columnOfPiece).getColour().equals(this.colour)) {
                    break;
                } else {
                    possibleMoves.add((row*8)+columnOfPiece);
                    break;
                }
            }
        }

        for (int row = rowOfPiece-1; row > -1; row--) {
            if (!(engine.isPlayable((row*8)+columnOfPiece))) {
                possibleMoves.add((row*8)+columnOfPiece);
            } else {
                if (engine.getPieceAtIndex((row*8)+columnOfPiece).getColour().equals(this.colour)) {
                    break;
                } else {
                    possibleMoves.add((row*8)+columnOfPiece);
                    break;
                }
            }
        }

        for (int column = columnOfPiece+1; column < 8; column++) {
            if (!(engine.isPlayable((rowOfPiece*8)+column))) {
                possibleMoves.add((rowOfPiece*8)+column);
            } else {
                if (engine.getPieceAtIndex((rowOfPiece*8)+column).getColour().equals(this.colour)) {
                    break;
                } else {
                    possibleMoves.add((rowOfPiece*8)+column);
                    break;
                }
            }
        }

        for (int column = columnOfPiece-1; column > -1; column--) {
            if (!(engine.isPlayable((rowOfPiece*8)+column))) {
                possibleMoves.add((rowOfPiece*8)+column);
            } else {
                if (engine.getPieceAtIndex((rowOfPiece*8)+column).getColour().equals(this.colour)) {
                    break;
                } else {
                    possibleMoves.add((rowOfPiece*8)+column);
                    break;
                }
            }
        }

        return possibleMoves;
    }
}
