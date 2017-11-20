package game.gui;

import game.GameModel;
import models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Road extends JPanel {
    private Image carDown;
    private Image carUp;
    private Image carBrake;

    private static final int carPixels = 64;
    private static final int scale = GameModel.internalLengthUnitSize/carPixels;
    private static final int lineLength = 32;

    private List<Car> cars;
    private PlayerCar playerCar;

    private int totalDistance;

    public Road(List<Car> otherCars, PlayerCar playerCar) {
        super();
//        this.totalDistance = totalDistance;
        setBackground(new Color(180,180,180));
        try {
            carDown = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_down.png"));
            carUp = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_up.png"));
            carBrake = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_brakes.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cars = otherCars;
        this.playerCar = playerCar;
    }

    public void updateCars(List<Car> cars, PlayerCar playerCar) {
        this.cars = cars;
        this.playerCar = playerCar;
    }

    private int calculateRelativeY(RenderableGameObject object) {
        return (int)Math.ceil((double)(object.getMetersFromStart() - playerCar.getMetersFromStart())/scale);
    }

    private int translateIntoPixelPosition(int relativePosition) {
        return getHeight() - (relativePosition + carPixels);
    }

    public void renderInterruptedLine(Graphics g, int currentDistance) {

    }

//    public void draw(Map<Integer, Car> cars) {
//        updateCars(cars);
//        repaint();
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Car car: cars) {
            int y = getHeight() - ( calculateRelativeY(car) + carPixels);
            int x = car.getCarLane() == CarLane.LEFT ? 0 : getWidth()/2 + 1;
            Image imageToDraw = car.getCarDirection() == CarDirection.NORTH ? carUp : carDown;
            switch(car.getCarDirection()) {
                case NORTH:
                    g.drawImage(imageToDraw, x, y, carPixels, carPixels, null, null);
                    break;
                case SOUTH:
                    g.drawImage(imageToDraw, x, y-carPixels+1, carPixels, carPixels, null, null);
                    break;
            }
//            g.drawImage(imageToDraw, x, y, carPixels, carPixels,null,null);
        }
        int playerX = playerCar.getCarLane() == CarLane.LEFT ? 0 : getWidth()/2 + 1;
        int playerY = getHeight() - carPixels;
        g.drawImage(carUp, playerX, playerY, carPixels, carPixels, null, null);

        int y = translateIntoPixelPosition((int)Math.ceil((double)(totalDistance - playerCar.getMetersFromStart())/scale));
        int x = getWidth()/2;

        y = y % (2*lineLength);

        if(y > lineLength)
            g.drawLine(x, 0, x, y-lineLength);

        int height = getHeight();

        for(int i=y; i<height; i+=2*lineLength) {
//            g.drawLine(x,i,x,i+lineLength);
            int end = Math.min(i+lineLength, getHeight()-1);
            g.drawLine(x, i, x, end);
        }
//        if(!carBrakes)
//            g.drawImage(carUp, playerX, playerY, carPixels, carPixels, null, null);
//        else
//            g.drawImage(carBrake, playerX, playerY, carPixels, carPixels, null, null);
//        g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight()-1);
//        carBrakes = false;
    }
}
