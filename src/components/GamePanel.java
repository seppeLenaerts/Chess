package components;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {

    Thread gameThread;
    final int FPS = 60;

    public static final int WIDTH = 1100, HEIGHT = 800;
    Color currentColor = Color.WHITE;

    ChessBoard board = new ChessBoard();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        try {
            board.draw(g2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GamePanel() throws IOException {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(java.awt.Color.BLACK);
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
}
