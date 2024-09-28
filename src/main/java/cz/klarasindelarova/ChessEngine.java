package cz.klarasindelarova;

import java.util.List;

public class ChessEngine {

    private Piece[] fields;

    public ChessEngine() {
        this.fields = new Piece[64];
    }

    public Piece[] getAllFields() {
        return this.fields;
    }

    public Piece getPieceAtIndex(int index) {
        return this.fields[index];
    }

    public void setPieceAtIndex(Piece piece, int index) {
        this.fields[index] = piece;
    }

    public void initialSetup() {
        this.fields[0] = new Rook("black");
        this.fields[1] = new Knight("black");
        this.fields[2] = new Bishop("black");
        this.fields[3] = new Queen("black");
        this.fields[4] = new King("black");
        this.fields[5] = new Bishop("black");
        this.fields[6] = new Knight("black");
        this.fields[7] = new Rook("black");
        for (int b = 8; b < 16; b++) {
            this.fields[b] = new Pawn("black");
        }
        for (int w = 48; w < 56; w++) {
            this.fields[w] = new Pawn("white");
        }
        this.fields[56] = new Rook("white");
        this.fields[57] = new Knight("white");
        this.fields[58] = new Bishop("white");
        this.fields[59] = new Queen("white");
        this.fields[60] = new King("white");
        this.fields[61] = new Bishop("white");
        this.fields[62] = new Knight("white");
        this.fields[63] = new Rook("white");
    }

    public List<Integer> getPossibleMoves(int index) {
        Piece clickedPiece = getPieceAtIndex(index);
        return clickedPiece.givePossibleMoves(this, index);
    }

    public boolean isPlayable(int index) {
        if (this.fields[index] == null) {
            return false;
        }
        return true;
    }

    /* public Piece[][] turnBoardIntoMultidimensionalArray() {
        Piece[][] multidimensionalFields = new Piece[8][8];
        int index = 0;
        for (int row = 0; row < multidimensionalFields.length; row++) {
            for (int column = 0; column < multidimensionalFields.length; column++) {
                multidimensionalFields[row][column] = this.fields[index];
                index = index + 1;
            }
        }
        return multidimensionalFields;
    } */

}
