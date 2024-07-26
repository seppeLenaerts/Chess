package components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;

public class ChessBoard extends JPanel {

    public static final int SQUARE_LENGTH = 90;

    public static final String RESOURCES_SQUARE_DARK_PNG = "resources/square_dark.png";
    public static final String RESOURCES_SQUARE_LIGHT_PNG = "resources/square_light.png";
    BufferedImage background;

    EnumMap<Piece, BufferedImage> blackPieces;
    EnumMap<Piece, BufferedImage> whitePieces;

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0,0,720,720,null);
        g.dispose();
    }

    public ChessBoard() throws IOException {
        prepareBoardBackground();
        preparePieces();
    }

    private void preparePieces() throws IOException {
        blackPieces = new EnumMap<>(Piece.class);
        whitePieces = new EnumMap<>(Piece.class);

        for(Piece p : Piece.values()) {
            BufferedImage image = ImageIO.read(new File(String.format("resources/%s_%s.png", "black", p.name().toLowerCase())));
            blackPieces.put(p, image);
            image = ImageIO.read(new File(String.format("resources/%s_%s.png", "white", p.name().toLowerCase())));
            whitePieces.put(p, image);
        }

    }

    private void prepareBoardBackground() throws IOException {
        setPreferredSize(new Dimension(720, 720));

        BufferedImage blackSquareImage = ImageIO.read(new File(RESOURCES_SQUARE_DARK_PNG));
        BufferedImage whiteSquareImage = ImageIO.read(new File(RESOURCES_SQUARE_LIGHT_PNG));

        background = new BufferedImage(720, 720, BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = background.getGraphics();

        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                if ((file + rank) % 2 == 1) {
                    graphics.drawImage(blackSquareImage, rank * 90, file * 90, 90, 90, null);
                } else {
                    graphics.drawImage(whiteSquareImage, rank * 90, file * 90, 90, 90, null);
                }
            }
        }

        graphics.dispose();
    }
}
