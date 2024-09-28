package cz.klarasindelarova;

import java.util.List;

public class Bishop extends Piece{

    public Bishop(String colour) {
        super(colour);
        super.name = "v";
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, int index) {
        return List.of();
    }
}
