package game.gui;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JLabel {
    public GameOverPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        setFont(new Font("Arial", Font.BOLD, 30));
        setText("GAME OVER");
        super.paintComponent(g);
    }
}
