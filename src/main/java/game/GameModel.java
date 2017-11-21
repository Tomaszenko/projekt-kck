package game;

import models.*;

import javax.print.attribute.standard.MediaSize;
import java.util.*;
import java.util.stream.Collectors;

public class GameModel {
    private MainMenu mainMenu;
    private TracksMenu tracksMenu;
    private CollisionMenu collisionMenu;
    private TrackCompletedMenu trackCompletedMenu;

    private PlayerCar playerCar;
    private List<OtherCar> otherCars;
    private Route currentRoute;

    private MyCollisionListener collisionListener;
    private MyFinishListener finishListener;

    public static final int internalLengthUnitSize = 256;

    public GameModel(MainMenu mainMenu, TracksMenu tracksMenu,
                     CollisionMenu collisionMenu, TrackCompletedMenu trackCompletedMenu,
                     PlayerCar playerCar, List<OtherCar> otherCars) {
        this.playerCar = playerCar;
        this.otherCars = otherCars;
        this.mainMenu = mainMenu;
        this.tracksMenu = tracksMenu;
        this.collisionMenu = collisionMenu;
        this.trackCompletedMenu = trackCompletedMenu;
    }

    public void reset() {
        playerCar.setDestroyed(false);
        playerCar.setMetersFromStart(0);
        playerCar.setSpeed(100);
        playerCar.setCarLane(CarLane.RIGHT);
        playerCar.setCarColor(CarColor.BLUE);
        playerCar.setCarDirection(CarDirection.NORTH);
        this.otherCars = new RandomCarsGenerator(0.5)
                .generateRandomCarsForRoute(currentRoute);
    }

    public void setCollisionListener(MyCollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    public void setFinishListener(MyFinishListener finishListener) {
        this.finishListener = finishListener;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public TracksMenu getTracksMenu() {
        return tracksMenu;
    }

    public void setTracksMenu(TracksMenu tracksMenu) {
        this.tracksMenu = tracksMenu;
    }

    public CollisionMenu getCollisionMenu() {
        return collisionMenu;
    }

    public void setCollisionMenu(CollisionMenu collisionMenu) {
        this.collisionMenu = collisionMenu;
    }

    public TrackCompletedMenu getTrackCompletedMenu() {
        return trackCompletedMenu;
    }

    public void setTrackCompletedMenu(TrackCompletedMenu trackCompletedMenu) {
        this.trackCompletedMenu = trackCompletedMenu;
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

    public void setCurrentRoute(Route route) {
        this.currentRoute = route;
        currentRoute.setDistance(currentRoute.getDistance()*internalLengthUnitSize);
        Map<Integer, String> map = currentRoute.getRoadSigns();
        Map<Integer, String> newMap = new TreeMap<>();
        System.out.println("KONSTRUKCJA MAPY");
        for(int i: map.keySet()) {
            System.out.println(i+": "+map.get(i));
            newMap.put(i*internalLengthUnitSize, map.get(i));
        }
        currentRoute.setRoadSigns(newMap);
//        this.otherCars = new RandomCarsGenerator(0.5)
//                .generateRandomCarsForRoute(route);
        reset();
    }

    public Route getCurrentRoute() {
        return this.currentRoute;
    }

    public void update() {
//        System.out.println("update");
        updateCar(playerCar);

        List<OtherCar> otherCarsLeft = otherCars.stream()
                .filter((car)->car.getCarLane() == CarLane.LEFT)
                .sorted((car1, car2)-> Integer.signum(car2.getMetersFromStart() - car1.getMetersFromStart()))
                .collect(Collectors.toList());

        List<OtherCar> otherCarsRight = otherCars.stream()
                .filter((car)->car.getCarLane() == CarLane.RIGHT)
                .sorted((car1, car2)-> Integer.signum(car1.getMetersFromStart() - car2.getMetersFromStart()))
                .collect(Collectors.toList());


        System.out.println("LEFT LANE");
        for(int ind=0; ind!=otherCarsLeft.size(); ++ind) {
            OtherCar car = otherCarsLeft.get(ind);
            if(ind < otherCarsLeft.size() - 1) {
                OtherCar nextCar = otherCarsLeft.get(ind+1);
                int diff = car.getMetersFromStart() - nextCar.getMetersFromStart();
                if(diff < 1024 + Math.random()*200 - 100)
                    car.brake(5);
                else {
                    if(diff < 2048 + Math.random()*200 - 100)
                        car.brake(2);
                    else {
                        car.accelerate();
                    }
                }
            }
            System.out.println(car.getMetersFromStart());
            updateCar(car);
        }


        System.out.println("RIGHT LANE");
        for(int ind=0; ind!=otherCarsRight.size(); ++ind) {
            OtherCar car = otherCarsRight.get(ind);
            if(ind < otherCarsRight.size() - 1) {
                OtherCar nextCar = otherCarsRight.get(ind+1);
                int diff = nextCar.getMetersFromStart() - car.getMetersFromStart();
                if(diff < 1024 + Math.random()*200 - 100)
                    car.brake(5);
                else {
                    if(diff < 2048 + Math.random()*200 - 100)
                        car.brake(2);
                    else {
                        car.accelerate();
                    }
                }
            }
            System.out.println(car.getMetersFromStart());
            updateCar(car);
        }

        checkForCollisionsNow();
//        checkForCollisionsAfterUpdate();

        if(!playerCar.isDestroyed()) {
            if (playerCar.getMetersFromStart() > currentRoute.getDistance()) {
                playerCar.setMetersFromStart(currentRoute.getDistance());
                finishListener.finish();
            }
        }
    }

    public void checkForCollisionsNow() {
        List<OtherCar> leftLaneCars = getOppositeDirectionCars();
        List<OtherCar> rightLaneCars = getSameDirectionCars();
        int distanceCovered = playerCar.getMetersFromStart();
        int playerCarLength = playerCar.getLength();
        CarLane ourLane = playerCar.getCarLane();

        if(ourLane == CarLane.LEFT) {
            for (OtherCar car : leftLaneCars) {
                int otherCarDistanceFromStart = car.getMetersFromStart();
                int otherCarLength = car.getLength();

                if ( (distanceCovered > otherCarDistanceFromStart) &&
                        (distanceCovered - playerCarLength < otherCarDistanceFromStart + otherCarLength) ) {
                    car.setMetersFromStart(distanceCovered);
                    System.out.println("crash left now");
                    playerCar.setDestroyed(true);
                    car.setDestroyed(true);
                    collisionListener.collision();
                    break;
                }
            }
        } else {
            for (OtherCar car : rightLaneCars) {
                int otherCarDistanceFromStart = car.getMetersFromStart();
                int otherCarLength = car.getLength();

                if ( (distanceCovered > otherCarDistanceFromStart - otherCarLength) &&
                        (distanceCovered - playerCarLength < otherCarDistanceFromStart) ) {
                    playerCar.setMetersFromStart(car.getMetersFromStart() - internalLengthUnitSize);
                    System.out.println("crash right now");
                    playerCar.setDestroyed(true);
                    car.setDestroyed(true);
                    collisionListener.collision();
                    break;
                }
            }
        }
    }

    private void checkForCollisionsAfterUpdate() {
        int distanceCovered = playerCar.getMetersFromStart();
        int playerCarLength = playerCar.getLength();
        CarLane ourLane = playerCar.getCarLane();

        int afterUpdateDistanceCovered = checkAfterUpdatePosition(playerCar);

        List<OtherCar> leftLaneCars = getOppositeDirectionCars();
        List<OtherCar> rightLaneCarsNearer = getSameDirectionCars()
                .stream()
                .filter(car -> car.getMetersFromStart() <= distanceCovered - playerCarLength)
                .collect(Collectors.toList());
        List<OtherCar> rightLaneCarsFurther = getSameDirectionCars()
                .stream()
                .filter(car -> car.getMetersFromStart() - car.getLength() >= distanceCovered)
                .collect(Collectors.toList());

        if(ourLane == CarLane.LEFT) {
            for (OtherCar car : leftLaneCars) {
                int afterUpdateOtherPosition = checkAfterUpdatePosition(car);

                if ( afterUpdateDistanceCovered > afterUpdateOtherPosition &&
                        afterUpdateDistanceCovered - playerCarLength <= afterUpdateOtherPosition + car.getLength()) {
//                    System.out.println("crash left update");
                    collisionListener.collision();
                    break;
                }
            }
        } else {
            for (OtherCar car : rightLaneCarsNearer) {
                int afterUpdateOtherPosition = checkAfterUpdatePosition(car);

                if ( afterUpdateOtherPosition > afterUpdateDistanceCovered - playerCarLength ) {
//                    System.out.println("crash right update nearer");
                    collisionListener.collision();
                    break;
                }
            }

            for(OtherCar car: rightLaneCarsFurther) {
                int otherCarLength = car.getLength();
                int afterUpdateOtherPosition = checkAfterUpdatePosition(car);

                if ( afterUpdateDistanceCovered > afterUpdateOtherPosition - otherCarLength ) {
//                    System.out.println("crash right update further");
                    collisionListener.collision();
                    break;
                }
            }
        }
    }

    public List<OtherCar> getSameDirectionCars() {
        return otherCars.stream().filter((car)->car.getCarDirection()==CarDirection.NORTH).collect(Collectors.toList());
    }

    public List<OtherCar> getOppositeDirectionCars() {
        return otherCars.stream().filter((car)->car.getCarDirection()==CarDirection.SOUTH).collect(Collectors.toList());
    }

    private int checkAfterUpdatePosition(Car car) {
        return car.getMetersFromStart() + deltaPositionChange(car);
    }

    private int deltaPositionChange(Car car) {
        int result = (int)(car.getSpeed());
        if(car.getCarDirection()==CarDirection.NORTH)
            return result;
        else
            return -result;
    }

    private void updateCar(Car car) {
        car.setMetersFromStart(car.getMetersFromStart() + deltaPositionChange(car));
    }
}
