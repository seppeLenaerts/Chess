package components.pieces;

import components.Color;

public class Queen extends Piece {

    public Queen(int col, int row, Color color) {
        super(col, row, color);
    }

    @Override
    public boolean legalMove(int col, int row) {
        if (Math.abs(preCol - col) == Math.abs(preRow - row)) {
            return true;
        } else if (preCol - col != 0 && preRow - row == 0) {
            return true;
        } else {
            return preCol - col == 0 && preRow - row != 0;
        }
    }
}
