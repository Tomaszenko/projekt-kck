package models;

import game.GameModel;

public class OtherCar extends Car {
    public OtherCar(int meters, int maxSpeed, int speed, CarColor carColor, CarLane carLane, CarDirection carDirection) {
        super(meters, GameModel.internalLengthUnitSize, maxSpeed, speed, carColor, carLane, carDirection);
    }
}
