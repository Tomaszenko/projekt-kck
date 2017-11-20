package models;

import game.GameModel;

public class PlayerCar extends Car {
    public PlayerCar(int speed, CarColor carColor, CarLane carLane) {
        super(0, GameModel.internalLengthUnitSize, 200, speed, carColor, carLane, CarDirection.NORTH);
    }
}
