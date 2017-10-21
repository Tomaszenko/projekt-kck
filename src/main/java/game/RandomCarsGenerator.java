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
        int numCars = (int)(density * distance / 100);

        List<OtherCar> result = new ArrayList<>();
        for(int i=0; i!=numCars; ++i) {
            CarLane lane = generateRandomCarLane();
            result.add(new OtherCar(
                    generateRandomDistanceFromStart(distance),
                    generateRandomSpeed(),
                    generateRandomColor(),
                    lane,
                    lane == CarLane.LEFT ? CarDirection.SOUTH : CarDirection.NORTH
                    ));
        }
        return result;
    }

    private int generateRandomDistanceFromStart(int totalDistance) {
        return (int) (Math.random() * totalDistance);
    }

    private int generateRandomSpeed() {
        return (int)(Math.random()*20) + 10;
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
