package components.pieces;

import components.Color;

public class Bishop extends Piece {
    public Bishop(int col, int row, Color color) {
        super(col, row, color);
    }

    @Override
    public boolean legalMove(int col, int row) {
        return Math.abs(preCol - col) == Math.abs(preRow - row);
    }
}
