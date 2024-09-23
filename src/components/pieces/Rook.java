package components.pieces;

import components.Color;

public class Rook extends Piece {
    public Rook(int col, int row, Color color) {
        super(col, row, color);
        value = 5;
    }

    @Override
    public boolean legalMove(int col, int row) {
        if (preCol - col != 0 && preRow - row == 0) {
            return true;
        } else {
            return preCol - col == 0 && preRow - row != 0;
        }
    }
}
