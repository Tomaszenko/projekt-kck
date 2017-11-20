package game.gui;

import game.GameModel;

import javax.swing.*;
import java.awt.*;

public class Distancemeter extends JLabel {
    private int distance;
    private int distanceToCover;

    public Distancemeter(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 20));
    }

    public Distancemeter(int distanceCovered, int distanceToCover) {
        this("Przejechano: "+String.valueOf(distanceCovered/GameModel.internalLengthUnitSize)+"km/"+distanceToCover/GameModel.internalLengthUnitSize+"km");
        this.distance = distanceCovered;
        this.distanceToCover = distanceToCover;
    }

    public void updateDistance(int currentDistance) {
        this.distance = currentDistance;
    }

    private String makeTextOfDistance(int distance) {
        return "Przejechano: " + String.valueOf(distance/GameModel.internalLengthUnitSize) + "km/" + distanceToCover/GameModel.internalLengthUnitSize + "km";
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
