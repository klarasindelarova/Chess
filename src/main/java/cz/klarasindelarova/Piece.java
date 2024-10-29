package cz.klarasindelarova;


import java.util.List;

public abstract class Piece {

    protected String name;
    protected String colour;
    protected String notation;

    public Piece(String name, String colour, String notation) {
        this.name = name;
        this.colour = colour;
        this.notation = notation;
    }

    public String getName() {
        return this.name;
    }

    public String getColour() {
        return this.colour;
    }

    public String getNotation() {
        return notation;
    }

    public abstract List<Integer> givePossibleMoves(Piece[] pieces, int index);

    public int getIndexFromRowAndColumn(int row, int column) {
        return 8 * row + column;
    }

    public boolean isInBounds(int row, int column) {
        return row >= 0 && row <= 7 && column >= 0 && column <= 7;
    }


    public Integer giveIndexOfFieldIfMoveIsOk(Piece[] pieces, int rowOfFutureMove, int columnOfFutureMove) {
        int indexFromRowAndColumn = getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove);
        if (!(MoveInspector.isPlayable(pieces, indexFromRowAndColumn))) {
            return indexFromRowAndColumn;
        } else {
            if (!(pieces[indexFromRowAndColumn].getColour().equals(this.colour))) {
                return indexFromRowAndColumn;
            }
        }
        return null;
    }

}
