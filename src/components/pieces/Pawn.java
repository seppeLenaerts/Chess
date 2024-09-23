package components.pieces;

import components.Color;

public class Pawn extends Piece {
    public Pawn(int col, int row, Color color) {
        super(col, row, color);
    }

    @Override
    public boolean legalMove(int col, int row) {
        if (color.equals(Color.WHITE)) {
            return preCol == col && (preRow - 1 == row || (firstMove && preRow - 2 == row));
        } else {
            return preCol == col && (preRow + 1 == row || (firstMove && preRow + 2 == row));
        }
    }
}
