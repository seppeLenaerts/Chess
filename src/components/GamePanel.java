package components;

import components.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {

    Thread gameThread;
    final int FPS = 60;

    public static final int WIDTH = 1020, HEIGHT = 720;
    Color currentColor = Color.WHITE;
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece selectedPiece;

    ChessBoard board = new ChessBoard();
    Mouse mouse = new Mouse();

    int whiteScore = 0;
    int blackScore = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(java.awt.Color.BLACK);

        addMouseMotionListener(mouse);
        addMouseListener(mouse);

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
        double drawInterval = (double) 1000000000/FPS;
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
        if (mouse.pressed) {
            handleMousePress();
        }

        if (!mouse.pressed && selectedPiece != null) {
            handleMouseRelease();
        }
    }

    private void handleMousePress() {
        if (selectedPiece == null) {
            List<Piece> list = simPieces.stream().filter(p ->
                    p.col == mouse.x / ChessBoard.SQUARE_SIZE
                            && p.row == mouse.y / ChessBoard.SQUARE_SIZE
                            && p.color.equals(currentColor)
            ).toList();
            if (!list.isEmpty()) {
                selectedPiece = list.get(0);
            }
        } else {
            simulate();
        }
    }

    private void handleMouseRelease() {
        Piece capturingPiece = isCapturingPiece();

        boolean legalMove;
        if (selectedPiece instanceof Pawn && capturingPiece != null) {
            legalMove = ((Pawn) selectedPiece).canTake(mouse.x / ChessBoard.SQUARE_SIZE, mouse.y / ChessBoard.SQUARE_SIZE);
        } else {
            legalMove = isLegalMove();
        }

        if (capturingPiece != null && capturingPiece.color.equals(currentColor)) {
            selectedPiece.resetPosition();
        } else if (!legalMove) {
            selectedPiece.resetPosition();
        } else {
            if (capturingPiece != null) {
                simPieces.remove(capturingPiece);
                if (capturingPiece.color.equals(Color.WHITE)) {
                    blackScore += capturingPiece.value;
                } else {
                    whiteScore += capturingPiece.value;
                }
            }
            selectedPiece.update(mouse.x, mouse.y);
            currentColor = currentColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
            copyPieces(simPieces, pieces);
        }
        selectedPiece = null;
    }

    private boolean isLegalMove() {
        return selectedPiece.legalMove(mouse.x / ChessBoard.SQUARE_SIZE, mouse.y / ChessBoard.SQUARE_SIZE);
    }

    private Piece isCapturingPiece() {
        List<Piece> list = simPieces.stream().filter(p ->
                p.col == mouse.x / ChessBoard.SQUARE_SIZE
                        && p.row == mouse.y / ChessBoard.SQUARE_SIZE
        ).toList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    private void simulate() {
        selectedPiece.x = mouse.x - ChessBoard.SQUARE_SIZE/2;
        selectedPiece.y = mouse.y - ChessBoard.SQUARE_SIZE/2;
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
        source.forEach(p -> target.add(p.clone()));
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
        g2.setColor(java.awt.Color.WHITE);
        if (selectedPiece != null) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.fillRect(
                    (mouse.x / ChessBoard.SQUARE_SIZE) * ChessBoard.SQUARE_SIZE,
                    (mouse.y / ChessBoard.SQUARE_SIZE) * ChessBoard.SQUARE_SIZE,
                    ChessBoard.SQUARE_SIZE,
                    ChessBoard.SQUARE_SIZE
            );
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            selectedPiece.draw(g2);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        if (currentColor.equals(Color.BLACK)) {
            g2.drawString("Black's turn", 800,200);
        } else {
            g2.drawString("White's turn", 800,200);
        }
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString(String.format("Black: %d", blackScore), 750, 300);
        g2.drawString(String.format("White: %d", whiteScore), 850, 300);

        g2.dispose();
    }
}
