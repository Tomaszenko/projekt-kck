package game.gui;

import models.Car;
import models.CarDirection;
import models.CarLane;
import models.PlayerCar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Road extends JPanel {
    private Image carDown;
    private Image carUp;

    private List<Car> cars;
    private PlayerCar playerCar;

    public Road(List<Car> otherCars, PlayerCar playerCar) {
        super();
        try {
            carDown = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_down.png"));
            carUp = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("car_up.png"));
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

//    public void draw(Map<Integer, Car> cars) {
//        updateCars(cars);
//        repaint();
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Car car: cars) {
            int y = getHeight() - (car.getMetersFromStart() - playerCar.getMetersFromStart() + 60);
            int x = car.getCarLane() == CarLane.LEFT ? 0 : getWidth()/2 + 1;
            Image imageToDraw = car.getCarDirection() == CarDirection.NORTH ? carUp : carDown;
            g.drawImage(imageToDraw, x, y, 50,50,null,null);
        }
        int playerX = playerCar.getCarLane() == CarLane.LEFT ? 0 : getWidth()/2 + 1;
        int playerY = getHeight() - 60;
        g.drawImage(carUp, playerX, playerY, 50, 50, null, null);
    }
}
