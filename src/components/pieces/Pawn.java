package components.pieces;

import components.Color;

public class Pawn extends Piece {
    public Pawn(int col, int row, Color color) {
        super(col, row, color);
        value = 1;
    }

    @Override
    public boolean legalMove(int col, int row) {
        if (color.equals(Color.WHITE)) {
            return preCol == col && (preRow - 1 == row || (firstMove && preRow - 2 == row));
        } else {
            return preCol == col && (preRow + 1 == row || (firstMove && preRow + 2 == row));
        }
    }

    public boolean canTake(int col, int row) {
        if (color.equals(Color.WHITE)) {
            return preRow - 1 == row && (preCol + 1 == col || preCol - 1 == col);
        } else {
            return preRow + 1 == row && (preCol + 1 == col || preCol - 1 == col);
        }
    }
}
