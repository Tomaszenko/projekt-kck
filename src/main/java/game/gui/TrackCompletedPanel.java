package game.gui;

import javax.swing.*;
import java.awt.*;

public class TrackCompletedPanel extends JLabel {
    public TrackCompletedPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        setFont(new Font("Arial", Font.BOLD, 30));
        setText("WELL DONE");
        super.paintComponent(g);
    }
}
