package models;

import game.CarColor;
import game.CarDirection;

public class OtherCar extends Car {
    public OtherCar(int width, int height, int speed, CarColor carColor, CarDirection carDirection, Location location) {
        super(width, height, speed, carColor, carDirection, location);
    }
}
