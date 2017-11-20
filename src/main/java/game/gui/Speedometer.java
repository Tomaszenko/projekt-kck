package game.gui;

import javax.swing.*;
import java.awt.*;

public class Speedometer extends JLabel {

    private int speed;

    public Speedometer(String text) {
        super(text);
        setFont(new Font("Arial", Font.BOLD, 20));
    }

    public Speedometer(int speed) {
        this("Speed: " + String.valueOf(speed) + "km/h");
    }

    public void updateSpeed(int currentSpeed) {
        this.speed = currentSpeed;
    }

    private String makeTextOfSpeed(int speed) {
        return "Speed: " + String.valueOf(speed) + "km/h";
    }

    public void draw(int speed) {
        if(this.speed != speed) {
            updateSpeed(speed);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        setText(makeTextOfSpeed(speed));
        super.paintComponent(g);
    }
}
