package cz.klarasindelarova;


import java.util.List;

public class Pawn extends Piece {

    public Pawn(String colour) {
        super(colour);
        super.name = "o";
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, int index) {


        return List.of();
    }
}
