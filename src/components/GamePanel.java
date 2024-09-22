package components;

import components.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    Thread gameThread;
    final int FPS = 60;

    public static final int WIDTH = 1020, HEIGHT = 720;
    Color currentColor = Color.WHITE;
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    ChessBoard board = new ChessBoard();

    public GamePanel() throws IOException {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(java.awt.Color.BLACK);

        initializePieces(Color.WHITE);
        initializePieces(Color.BLACK);

        copyPieces(pieces, simPieces);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currTime;

        while (gameThread != null) {
            currTime = System.nanoTime();
            delta += (currTime - lastTime)/drawInterval;
            lastTime = currTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {

    }

    private void initializePieces(Color color) {
        pieces.add(new Pawn(0, color == Color.WHITE ? 6 : 1, color));
        pieces.add(new Pawn(1, color == Color.WHITE ? 6 : 1, color));
        pieces.add(new Pawn(2, color == Color.WHITE ? 6 : 1, color));
        pieces.add(new Pawn(3, color == Color.WHITE ? 6 : 1, color));
        pieces.add(new Pawn(4, color == Color.WHITE ? 6 : 1, color));
        pieces.add(new Pawn(5, color == Color.WHITE ? 6 : 1, color));
        pieces.add(new Pawn( 6, color == Color.WHITE ? 6 : 1, color));
        pieces.add(new Pawn(7, color == Color.WHITE ? 6 : 1, color));

        pieces.add(new Rook(0, color == Color.WHITE ? 7 : 0, color));
        pieces.add(new Knight(1, color == Color.WHITE ? 7 : 0, color));
        pieces.add(new Bishop(2, color == Color.WHITE ? 7 : 0, color));
        pieces.add(new Queen(3, color == Color.WHITE ? 7 : 0, color));
        pieces.add(new King(4, color == Color.WHITE ? 7 : 0, color));
        pieces.add(new Bishop(5, color == Color.WHITE ? 7 : 0, color));
        pieces.add(new Knight(6, color == Color.WHITE ? 7 : 0, color));
        pieces.add(new Rook(7, color == Color.WHITE ? 7 : 0, color));
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        target.addAll(source);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        try {
            board.draw(g2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Piece p : simPieces) {
            p.draw(g2);
        }
    }
}
