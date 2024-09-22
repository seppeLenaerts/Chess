package components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessBoard {

    final int FILE = 8, RANK = 8;
    public static final int SQUARE_SIZE = 100;

    public static final String RESOURCES_SQUARE_DARK_PNG = "resources/square_dark.png";
    public static final String RESOURCES_SQUARE_LIGHT_PNG = "resources/square_light.png";
    BufferedImage background;

    public void draw(Graphics2D g2) throws IOException {
        BufferedImage blackSquareImage = ImageIO.read(new File(RESOURCES_SQUARE_DARK_PNG));
        BufferedImage whiteSquareImage = ImageIO.read(new File(RESOURCES_SQUARE_LIGHT_PNG));

        background = new BufferedImage(FILE*SQUARE_SIZE, RANK*SQUARE_SIZE, BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = background.getGraphics();

        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                if ((file + rank) % 2 == 1) {
                    graphics.drawImage(blackSquareImage, rank * SQUARE_SIZE, file * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
                } else {
                    graphics.drawImage(whiteSquareImage, rank * SQUARE_SIZE, file * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
                }
            }
        }

        g2.drawImage(background, 0,0, null);
        graphics.dispose();
    }
}
