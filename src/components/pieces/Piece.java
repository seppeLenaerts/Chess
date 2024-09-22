package components.pieces;

import components.ChessBoard;
import components.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Piece {
    public static final String RESOURCE_PATH = "resources/%s_%s.png";

    public BufferedImage image;
    public int x, y;
    public int col, row;
    public int preCol, preRow;
    public Color color;

    public Piece(int col, int row, Color color) {
        this.col = col;
        this.row = row;
        this.preCol = col;
        this.preRow = row;
        this.color = color;
        this.x = getX();
        this.y = getY();
        try {
            setImage(ImageIO.read(new File(String.format(RESOURCE_PATH, color.name(), getClass().getSimpleName()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getX() {
        return ChessBoard.SQUARE_SIZE * col;
    }

    public int getY() {
        return ChessBoard.SQUARE_SIZE * row;
    }

    public int getCol() {
        return (x / ChessBoard.SQUARE_SIZE);
    }

    public int getRow() {
        return (y / ChessBoard.SQUARE_SIZE);
    }

    public void update(int x, int y) {
        this.x = (x / ChessBoard.SQUARE_SIZE) * ChessBoard.SQUARE_SIZE;
        this.y = (y / ChessBoard.SQUARE_SIZE) * ChessBoard.SQUARE_SIZE;
        col = getCol();
        row = getRow();
        preCol = col;
        preRow = row;
    }

    public boolean legalMove(int col, int row) {
        return false;
    }

    public void resetPosition() {
        this.x = getX();
        this.y = getY();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, ChessBoard.SQUARE_SIZE, ChessBoard.SQUARE_SIZE, null);
    }
}
