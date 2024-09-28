package cz.klarasindelarova;


import java.util.*;

public abstract class Piece {

    protected String name;
    protected String colour;

    public Piece(String colour) {
        this.colour = colour;
    }

    public String getName() {
        return this.name;
    }

    public String getColour() {
        return this.colour;
    }

    public abstract List<Integer> givePossibleMoves(ChessEngine engine, int index);

}
