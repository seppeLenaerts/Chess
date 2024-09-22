package components.pieces;

import components.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Rook extends Piece {
    public Rook(int col, int row, Color color) {
        super(col, row, color);
        try {
            setImage(ImageIO.read(new File(String.format(RESOURCE_PATH, color.name(), getClass().getName()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
