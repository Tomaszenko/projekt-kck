package models;

public class PlayerCar extends Car {
    public PlayerCar(int speed, CarColor carColor, CarLane carLane) {
        super(0, speed, carColor, carLane, CarDirection.NORTH);
    }
}
