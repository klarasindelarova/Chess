package cz.klarasindelarova;


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


}
