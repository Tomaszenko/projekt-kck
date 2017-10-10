package models;

public class Car {
    private int km;
    private int speed;
    private int maxSpeed;
    private CarColor carColor;
    private CarLane carLane;

    public Car(int km, int speed, CarColor carColor, CarLane carLane) {
        this.km = km;
        this.speed = speed;
        this.carColor = carColor;
        this.carLane = carLane;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
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

    public CarLane getCarLane() {
        return carLane;
    }

    public void setCarLane(CarLane carLane) {
        this.carLane = carLane;
    }

    public void accelerate() {
        if (speed + 10 <= maxSpeed) {
            speed += 10;
        }
    }

    public void brake() {
        if (speed - 10 > 0) {
            speed -= 10;
        }
    }
}
