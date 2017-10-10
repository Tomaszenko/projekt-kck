package game;

import models.Car;
import models.GameMenu;
import models.OtherCar;
import models.PlayerCar;

import java.util.List;

public class GameModel {
    private GameMenu menu;
    private PlayerCar playerCar;
    private List<OtherCar> otherCars;

    public GameModel(GameMenu gameMenu, PlayerCar playerCar, List<OtherCar> otherCars) {
        this.playerCar = playerCar;
        this.otherCars = otherCars;
        this.menu = gameMenu;
    }

    public GameMenu getMenuModel() {
        return menu;
    }

    public void setMenuModel(GameMenu menuModel) {
        this.menu = menuModel;
    }

    public PlayerCar getPlayerCar() {
        return playerCar;
    }

    public void setPlayerCar(PlayerCar playerCar) {
        this.playerCar = playerCar;
    }

    public List<OtherCar> getOtherCars() {
        return otherCars;
    }

    public void setOtherCars(List<OtherCar> otherCars) {
        this.otherCars = otherCars;
    }

    public void update() {
        updateCar(playerCar);

        for(Car otherCar: otherCars) {
            updateCar(otherCar);
        }
    }

    private void updateCar(Car car) {
//        Location currentPlayerLocation = playerCar.getLocation();
//        CarDirection currentPlayerDirection = playerCar.getCarDirection();
//
//        switch(currentPlayerDirection) {
//            case NORTH:
//                playerCar.setLocation(
//                        new Location(currentPlayerLocation.getX(),
//                                currentPlayerLocation.getY() - playerCar.getSpeed())
//                );
//            case EAST:
//                playerCar.setLocation(
//                        new Location(currentPlayerLocation.getX() + playerCar.getSpeed(),
//                                currentPlayerLocation.getY() - playerCar.getSpeed())
//                );
//            case SOUTH:
//                playerCar.setLocation(
//                        new Location(currentPlayerLocation.getX(),
//                                currentPlayerLocation.getY() + playerCar.getSpeed())
//                );
//            case WEST:
//                playerCar.setLocation(
//                        new Location(currentPlayerLocation.getX() - playerCar.getSpeed(),
//                                currentPlayerLocation.getY())
//                );
//        }
    }
}
