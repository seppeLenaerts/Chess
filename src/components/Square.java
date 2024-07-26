package components;

import javax.swing.*;

public class Square {
    ImageIcon squareBackground;
    public Square(Color color) {
        if (Color.BLACK == color) {
            squareBackground = new ImageIcon("resources/square_dark.png");
        } else {
            squareBackground = new ImageIcon("resources/square_light.png");
        }
    }

}
