package components.pieces;

import components.Color;

public class Knight extends Piece {
    public Knight(int col, int row, Color color) {
        super(col, row, color);
    }

    @Override
    public boolean legalMove(int col, int row) {
        if (Math.abs(preRow - row) == 2 && Math.abs(preCol - col) == 1) {
            return true;
        } else {
            return Math.abs(preRow - row) == 1 && Math.abs(preCol - col) == 2;
        }
    }
}
