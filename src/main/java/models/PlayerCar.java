package models;

import game.CarColor;
import game.CarDirection;
import models.Car;

public class PlayerCar extends Car {
    public PlayerCar(int width, int height, int speed, CarColor carColor, CarDirection carDirection, Location location) {
        super(width, height, speed, carColor, carDirection, location);
    }
}
