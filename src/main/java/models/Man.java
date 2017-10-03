package models;

public class Man {
    private int strength;
    private int speed;
    private int hp;

    public Man(int strength, int speed, int hp) {
        this.strength = strength;
        this.speed = speed;
        this.hp = hp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
