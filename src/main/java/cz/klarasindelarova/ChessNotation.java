package cz.klarasindelarova;


public class ChessNotation {

    private String recordOfGame = "";
    private int numberOfRound = 0;


    public String getRecordOfGame() {
        return this.recordOfGame;
    }

    public void addRecordOfMoveToNotation(Piece movedPiece, int targetIndex, boolean isKingInCheck, boolean isCheckMate, boolean isWhiteTurn) {
        String recordOfSingleMove = createRecordOfSingleMove(movedPiece, targetIndex, isKingInCheck, isCheckMate, isWhiteTurn);
        this.recordOfGame = recordOfGame + recordOfSingleMove;
    }

    public String createRecordOfSingleMove(Piece movedPiece, int targetIndex, boolean isKingInCheck, boolean isCheckMate, boolean isWhiteTurn) {
        if (isWhiteTurn) {
            numberOfRound = numberOfRound + 1;
            if (isCheckMate) {
                return numberOfRound + ".    " + getRecordOfMove(movedPiece, targetIndex) + "#   ";
            }
            if (isKingInCheck) {
                return numberOfRound + ".    " + getRecordOfMove(movedPiece, targetIndex) + "+   ";
            }
            return numberOfRound + ".    " + getRecordOfMove(movedPiece, targetIndex) + "   ";
        } else {
            if (isCheckMate) {
                return getRecordOfMove(movedPiece, targetIndex) + "# \r\n";
            } else if (isKingInCheck) {
                return getRecordOfMove(movedPiece, targetIndex) + "+ \r\n";
            }
            return getRecordOfMove(movedPiece, targetIndex) + "\r\n";
        }
    }

    public String getRecordOfMove(Piece movedPiece, int targetIndex) {
        return movedPiece.getNotation() +
                getColumnFromIndex(targetIndex) +
                getRowFromIndex(targetIndex);
    }

    public String getColumnFromIndex(int index) {
        int column = index % 8;
        return switch (column) {
            case 0 -> "a";
            case 1 -> "b";
            case 2 -> "c";
            case 3 -> "d";
            case 4 -> "e";
            case 5 -> "f";
            case 6 -> "g";
            case 7 -> "h";
            default -> "Invalid column";
        };
    }

    public String getRowFromIndex(int index) {
        int row = index / 8;
        return switch (row) {
            case 0 -> "8";
            case 1 -> "7";
            case 2 -> "6";
            case 3 -> "5";
            case 4 -> "4";
            case 5 -> "3";
            case 6 -> "2";
            case 7 -> "1";
            default -> "Invalid row";
        };
    }

}
