package cz.klarasindelarova;

public enum Colour {

    WHITE, BLACK;

    public Colour opposite() {
        if (this == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }

    public String getCode() {
        if (this == WHITE) {
            return "WHITE";
        } else {
            return "BLACK";
        }
    }
}
