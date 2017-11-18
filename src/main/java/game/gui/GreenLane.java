package game.gui;

import models.Car;
import models.CarDirection;
import models.CarLane;
import models.RoadSign;

import javax.swing.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GreenLane extends JPanel {
    private Map<Integer, String> roadSigns;
    private int playerDistance;

    public GreenLane(Map<Integer, String> roadSigns, int playerDistance) {
        super();
        this.roadSigns = roadSigns;
        this.playerDistance = playerDistance;
    }

    public void updateSigns(Map<Integer, String> roadSignsToDraw, int playerDistance) {
        this.roadSigns = roadSignsToDraw;
        this.playerDistance = playerDistance;
    }

    public void draw(Map<Integer, String> roadSigns, int playerDistance) {
        updateSigns(roadSigns, playerDistance);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Map.Entry<Integer, String> entry: roadSigns.entrySet()) {
            int y = getHeight() - (entry.getKey() - playerDistance + 60);
            int x = 0;
            g.drawString(entry.getValue(), x, y);
        }

//        for(Map.Entry<Integer, String> entry: roadSigns.entrySet()) {
//            g.drawString(entry.getValue(), 0, entry.getKey());
//        }
    }
}
