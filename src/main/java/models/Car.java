package models;

import game.CarColor;
import game.CarDirection;

public class Car {
    private int width;
    private int height;
    private int speed;
    private CarColor carColor;
    private CarDirection carDirection;
    private Location location;

    public Car(int width, int height, int speed, CarColor carColor, CarDirection carDirection, Location location) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.carColor = carColor;
        this.carDirection = carDirection;
        this.location = location;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public CarColor getCarColor() {
        return carColor;
    }

    public void setCarColor(CarColor carColor) {
        this.carColor = carColor;
    }

    public CarDirection getCarDirection() {
        return carDirection;
    }

    public void setCarDirection(CarDirection carDirection) {
        this.carDirection = carDirection;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
