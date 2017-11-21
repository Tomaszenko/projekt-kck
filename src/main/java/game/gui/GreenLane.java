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
        setBackground(new Color(20, 140, 30));
        this.roadSigns = roadSigns;
        this.playerDistance = playerDistance;
    }

    public void updateSigns(int playerDistance) {
        this.playerDistance = playerDistance;
        System.out.println("UPDATE ZNAKÓW");
        for(Map.Entry<Integer, String> entry: roadSigns.entrySet()) {
            System.out.println(entry.getKey()+": "+entry.getValue());
        }
        System.out.println(playerDistance);
    }

    public void draw(Map<Integer, String> roadSigns, int playerDistance) {
        updateSigns(playerDistance);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Map.Entry<Integer, String> entry: roadSigns.entrySet()) {
            int y = getHeight() - ((entry.getKey() - playerDistance)/MyGameFrame.SCALE + MyGameFrame.CAR_PIXELS);
            if( y>0 && y<getHeight()) {
                int x = 0;
                Font f = new Font("Verdana", Font.BOLD, 20);
                g.setFont(f);
                g.setColor(new Color(255, 255, 255));
                g.fillRect(0, y, getFontMetrics(f).stringWidth(entry.getValue() + 2), getFontMetrics(f).getHeight() + 2);
                g.setColor(new Color(0, 0, 0));
                g.drawString(entry.getValue(), x + 1, y + 20);
                g.setColor(new Color(255, 255, 255));
                int xcenter = (getFontMetrics(f).stringWidth(entry.getValue() + 2)) / 2;
                g.drawLine(xcenter, y + 21, xcenter, y + 50);
            }
        }

//        for(Map.Entry<Integer, String> entry: roadSigns.entrySet()) {
//            g.drawString(entry.getValue(), 0, entry.getKey());
//        }
    }
}
