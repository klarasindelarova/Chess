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


    // TODO metoda by mela vracet int a kazda figura at si to prida do svych possibleMoves
    public void checkFiguresAroundAndAddMovesToList(Piece[] pieces, List<Integer> possibleMoves, int rowOfFutureMove, int columnOfFutureMove) {
        if (!(MoveInspector.isPlayable(pieces, getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)))) {
            possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
        } else {
            if (!(pieces[getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove)].getColour().equals(this.colour))) {
                possibleMoves.add(getIndexFromRowAndColumn(rowOfFutureMove, columnOfFutureMove));
            }
        }
    }

}
