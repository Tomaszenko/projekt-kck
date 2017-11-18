package game;

import models.*;
import services.RouteService;

import java.util.Map;
import java.util.TreeMap;

import static game.GameState.*;

public class GameController implements MyKeyListener, MyCollisionListener, MyFinishListener {

    private boolean running = false;

    private GameState gameState = MENU;
    private GameModel gameModel;
    private GameView gameView;

    private static GameController gameController = null;

    public static GameController getGameControllerInstance(GameModel gameModel) {
        if(gameController == null)
            gameController = new GameController(gameModel);
        return gameController;
    }

    private GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    synchronized public void updateGame() {
        if(gameState == GAME && running)
            updateModel();
        updateView();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void updateView() {
//        System.out.println("controller updates view");
        gameView.render(gameModel, gameState);
//        System.out.println("controller updated view");
    }

    public void updateModel() {
        gameModel.update();
    }

    @Override
    public void left() {
        switch(gameState) {
            case GAME:
                leftLane();
                break;
            default:
        }
    }

    @Override
    public void right() {
        switch(gameState) {
            case GAME:
                rightLane();
                break;
            default:
        }
    }

    @Override
    public void up() {
        switch(gameState) {
            case GAME:
                accelerate();
                break;
            case MENU:
                gameModel.getMainMenu().setPreviousItem();
                break;
            case TRACKS:
                gameModel.getTracksMenu().setPreviousItem();
                break;
            case COLLISION:
                gameModel.getCollisionMenu().setPreviousItem();
                break;
            case FINISHED:
                gameModel.getTrackCompletedMenu().setPreviousItem();
                break;
            default:
        }
    }

    @Override
    public void down() {
        switch(gameState) {
            case GAME:
                brake();
                break;
            case MENU:
                gameModel.getMainMenu().setNextItem();
                break;
            case TRACKS:
                gameModel.getTracksMenu().setNextItem();
                break;
            case COLLISION:
                gameModel.getCollisionMenu().setNextItem();
                break;
            case FINISHED:
                gameModel.getTrackCompletedMenu().setNextItem();
                break;
            default:
        }
    }

    @Override
    public void space() {
        switch(gameState) {
            case GAME:
                running = false;
                break;
            default:
        }
    }

    @Override
    public void enter() {
        switch(gameState) {
            case MENU:
                System.out.println("Key pressed, juhu");
                MainMenuItem mainMenuItem = gameModel.getMainMenu().getSelected();
                System.out.println("item selected=" + mainMenuItem.getName());
                switch (mainMenuItem) {
                    case QUIT:
                        quitGame();
                        break;
                    case NEW_GAME:
                        selectTracks();
                        break;
                }
                break;
            case TRACKS:
                System.out.println("Track selected, juhu");
                String filename = "track" + (gameModel.getTracksMenu().getPosition() + 1) + ".json";
                try {
                    Route selectedRoute = RouteService.getRouteFromFile(filename);
//                    selectedRoute.setDistance(5*selectedRoute.getDistance());
//                    Map<Integer, String> map = selectedRoute.getRoadSigns();
//                    Map<Integer, String> newMap = new TreeMap<>();
//                    for(int i: map.keySet()) {
//                        newMap.put(5*i, map.get(i));
//                    }
//                    selectedRoute.setRoadSigns(newMap);
                    gameModel.setCurrentRoute(selectedRoute);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                gameState = GAME;
                running = true;
                break;
            case COLLISION:
                CollisionMenuItem collisionMenuItem = gameModel.getCollisionMenu().getSelected();
                switch (collisionMenuItem) {
                    case GO_TO_MENU:
                        goToMainMenu();
                        break;
                    case TRY_AGAIN:
                        gameModel.reset();
//                        gameModel.setCurrentRoute(gameModel.getCurrentRoute());
                        gameState = GAME;
                        running = true;
                        break;
                }
                break;
            case FINISHED:
                TrackCompletedMenuItem trackCompletedMenuItem = gameModel.getTrackCompletedMenu().getSelected();
                switch (trackCompletedMenuItem) {
                    case GO_TO_MENU:
                        goToMainMenu();
                        break;
                    case TRY_AGAIN:
                        gameModel.reset();
//                        gameModel.setCurrentRoute(gameModel.getCurrentRoute());
                        gameState = GAME;
                        running = true;
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void escape() {
        switch (gameState) {
            case GAME:
                goToMainMenu();
                break;
            case MENU:
                quitGame();
                break;
            case TRACKS:
                goToMainMenu();
                break;
            case COLLISION:
                goToMainMenu();
                break;
            case FINISHED:
                goToMainMenu();
                break;
            default:
        }
    }

    @Override
    synchronized public void collision() {
        updateView();
        gameOver();
    }

    @Override
    public void finish() {
        updateView();
        wellDone();
    }

    synchronized private void leftLane() {
        gameModel.getPlayerCar().setCarLane(CarLane.LEFT);
        gameModel.checkForCollisionsNow();
    }

    synchronized private void rightLane() {
        gameModel.getPlayerCar().setCarLane(CarLane.RIGHT);
        gameModel.checkForCollisionsNow();
    }

    private void accelerate() {
        gameModel.getPlayerCar().accelerate();
    }

    private void brake() {
        gameModel.getPlayerCar().brake(2);
    }

    private void playGame() {
        gameState = GAME;
    }

    private void selectTracks() {
        gameState = TRACKS;
    }

    private void goToMainMenu() {
        gameState = MENU;
    }

    private void gameOver() {
        gameState = COLLISION;
    }

    private void wellDone() { gameState = FINISHED; }

    private void quitGame() {
        System.exit(0);
    }
}
