package game.gui;

import javax.swing.*;
import java.awt.*;

public class Distancemeter extends JLabel {
    private int distance;
    private int distanceToCover;

    public Distancemeter(String text) {
        super(text);
    }

    public Distancemeter(int distanceCovered, int distanceToCover) {
        this("Distance: "+String.valueOf(distanceCovered)+"m/"+distanceToCover+"m");
        this.distance = distanceCovered;
        this.distanceToCover = distanceToCover;
    }

    public void updateDistance(int currentDistance) {
        this.distance = currentDistance;
    }

    private String makeTextOfDistance(int distance) {
        return "Distance: " + String.valueOf(distance) + "m/" + distanceToCover + "m";
    }

    public void draw(int distance) {
        if(this.distance != distance) {
            updateDistance(distance);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        setText(makeTextOfDistance(distance));
        super.paintComponent(g);
    }
}
