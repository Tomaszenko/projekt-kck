package game.gui;

import javax.swing.*;
import java.awt.*;

public class RoadRightSide extends JPanel {
    public RoadRightSide() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0,0,0,getHeight()-1);
    }
}
