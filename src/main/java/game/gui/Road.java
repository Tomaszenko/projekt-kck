package game.gui;

import game.GameModel;
import models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Road extends JPanel {
    private Image carGreenUp;
    private Image carPinkUp;
    private Image carRedUp;
    private Image carBlueUp;
    private Image carYellowUp;

    private Image carGreenDown;
    private Image carPinkDown;
    private Image carRedDown;
    private Image carBlueDown;
    private Image carYellowDown;


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
            carGreenDown = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_green_down.png"));
            carGreenUp = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_green_up.png"));

            carBlueDown = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_blue_down.png"));
            carBlueUp = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_blue_up.png"));

            carRedDown = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_red_down.png"));
            carRedUp = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_red_up.png"));

            carYellowDown = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_yellow_down.png"));
            carYellowUp = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_yellow_up.png"));

            carPinkDown = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_pink_down.png"));
            carPinkUp = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_pink_up.png"));
//            carBrake = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_brakes.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cars = otherCars;
        this.playerCar = playerCar;
    }

    public void updateCars(List<Car> cars, PlayerCar playerCar) {
        this.cars = cars;
        this.playerCar = playerCar;

        System.out.println("Aktualny dystans: "+playerCar.getMetersFromStart()/GameModel.internalLengthUnitSize);
    }

    private int calculateRelativeY(RenderableGameObject object) {
        return (int)Math.ceil((double)(object.getMetersFromStart() - playerCar.getMetersFromStart())/scale);
    }

    private int translateIntoPixelPosition(int relativePosition) {
        return getHeight() - (relativePosition + carPixels + 10);
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
            int y = getHeight() - ( calculateRelativeY(car) + carPixels + 10);
            int x = car.getCarLane() == CarLane.LEFT ? 0 : getWidth()/2 + 1;
            switch(car.getCarColor()) {
                case GREEN:
                    switch (car.getCarDirection()) {
                        case NORTH:
                            g.drawImage(carGreenUp, x, y, carPixels, carPixels, null, null);
                            break;
                        case SOUTH:
                            g.drawImage(carGreenDown, x, y-carPixels+1, carPixels, carPixels, null, null);
                            break;
                    }
                    break;
                case BLUE:
                    switch (car.getCarDirection()) {
                        case NORTH:
                            g.drawImage(carBlueUp, x, y, carPixels, carPixels, null, null);
                            break;
                        case SOUTH:
                            g.drawImage(carBlueDown, x, y-carPixels+1, carPixels, carPixels, null, null);
                            break;
                    }
                    break;
                case RED:
                    switch (car.getCarDirection()) {
                        case NORTH:
                            g.drawImage(carRedUp, x, y, carPixels, carPixels, null, null);
                            break;
                        case SOUTH:
                            g.drawImage(carRedDown, x, y-carPixels+1, carPixels, carPixels,null, null);
                            break;
                    }
                    break;
                case YELLOW:
                    switch (car.getCarDirection()) {
                        case NORTH:
                            g.drawImage(carYellowUp, x, y, carPixels, carPixels, null, null);
                            break;
                        case SOUTH:
                            g.drawImage(carYellowDown, x, y-carPixels+1, carPixels, carPixels, null, null);
                            break;
                    }
                    break;
                case PINK:
                    switch (car.getCarDirection()) {
                        case NORTH:
                            g.drawImage(carPinkUp, x, y, carPixels, carPixels, null, null);
                            break;
                        case SOUTH:
                            g.drawImage(carPinkDown, x, y-carPixels+1, carPixels, carPixels, null, null);
                            break;
                    }
                    break;
            }
//            Image imageToDraw = car.getCarDirection() == CarDirection.NORTH ? carGreenUp : carGreenDown;
//            switch(car.getCarDirection()) {
//                case NORTH:
//                    g.drawImage(imageToDraw, x, y, carPixels, carPixels, null, null);
//                    break;
//                case SOUTH:
//                    g.drawImage(imageToDraw, x, y-carPixels+1, carPixels, carPixels, null, null);
//                    break;
//            }
//            g.drawImage(imageToDraw, x, y, carPixels, carPixels,null,null);
        }
        int playerX = playerCar.getCarLane() == CarLane.LEFT ? 0 : getWidth()/2 + 1;
        int playerY = getHeight() - carPixels - 10;
        g.drawImage(carBlueUp, playerX, playerY, carPixels, carPixels, null, null);

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
