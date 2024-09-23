package components.pieces;

import components.Color;

public class King extends Piece {
    public King(int col, int row, Color color) {
        super(col, row, color);
    }

    @Override
    public boolean legalMove(int col, int row) {
        return (Math.abs(preCol - col) <= 1 && Math.abs(preRow - row) <= 1);
    }
}
