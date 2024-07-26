import components.ChessBoard;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        JPanel chessBoard = new ChessBoard();
        frame.setContentPane(chessBoard);
        frame.pack();
        frame.setLayout(null);
        frame.setVisible(true);
    }
}