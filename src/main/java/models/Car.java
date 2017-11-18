package models;

public abstract class Car implements RenderableGameObject{
    private int metersFromStart;
    private int speed;
    private int maxSpeed;
    private int length;
    private boolean destroyed;
    private CarColor carColor;
    private CarLane carLane;
    private CarDirection carDirection;

    public Car(int metersFromStart, int maxSpeed, int speed, CarColor carColor, CarLane carLane, CarDirection carDirection) {
        this.metersFromStart = metersFromStart;
        this.speed = speed;
        this.maxSpeed = maxSpeed;
        this.length = 50;
        this.destroyed = false;
        this.carColor = carColor;
        this.carLane = carLane;
        this.carDirection = carDirection;
    }

    @Override
    public int getMetersFromStart() {
        return metersFromStart;
    }

    public void setMetersFromStart(int metersFromStart) {
        this.metersFromStart = metersFromStart;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public CarDirection getCarDirection() {
        return carDirection;
    }

    public void setCarDirection(CarDirection carDirection) {
        this.carDirection = carDirection;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public void accelerate() {
        speed = Math.min(speed + Math.max((int)(((float)(maxSpeed-speed)/maxSpeed) * 5),1), maxSpeed);
    }

    public void brake(int strength) {
        if (speed - strength > 0)
            speed -= strength;
    }
}
