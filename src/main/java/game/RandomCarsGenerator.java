package game;

import models.*;

import java.util.ArrayList;
import java.util.List;

public class RandomCarsGenerator {

    private double density;

    public RandomCarsGenerator(double density) {
        this.density = density;
    }

    public List<OtherCar> generateRandomCarsForRoute(Route route) {
        int distance = route.getDistance();
//        int numCars = (int)(density * distance / 100);

        List<OtherCar> result = new ArrayList<>();

        int currentDistance = 0;

        while(currentDistance < 2 * distance) {
            CarLane lane = generateRandomCarLane();

            int nextDistance = generateRandomDistanceFromStart(currentDistance, distance);
            int maxSpeed = generateRandomMaxSpeed();

            result.add(new OtherCar(
                    nextDistance,
                    maxSpeed,
                    generateRandomSpeed(maxSpeed),
                    generateRandomColor(),
                    lane,
                    lane == CarLane.LEFT ? CarDirection.SOUTH : CarDirection.NORTH
            ));

            currentDistance = nextDistance;
        }

        return result;
    }

    private int generateRandomDistanceFromStart(int currentDistance, int totalDistance) {
        return currentDistance + (int) ((Math.random()*0.03 + 0.02)*totalDistance);
    }

    private int generateRandomMaxSpeed() {
        return (int)(Math.random()*80) + 100;
    }

    private int generateRandomSpeed(int maxSpeed) {
        return (int)(Math.random()*(maxSpeed-50)) + 50;
    }

    private CarColor generateRandomColor() {
        int randomNumber = (int)(Math.random()*CarColor.values().length);
        return CarColor.values()[randomNumber];
    }

    private CarLane generateRandomCarLane() {
        return CarLane.values()[Math.random()>0.5?1:0];
    }

    private CarDirection generateRandomCarDirection() {
        return CarDirection.values()[Math.random()>0.5?1:0];
    }


}
