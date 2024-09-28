package cz.klarasindelarova;

import java.util.List;

public class Queen extends Piece{

    public Queen(String colour) {
        super(colour);
        super.name = "w";
    }

    @Override
    public List<Integer> givePossibleMoves(ChessEngine engine, int index) {


        return List.of();
    }
}

