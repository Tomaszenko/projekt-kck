package game;

import models.CarLane;
import models.MenuItem;

import static game.GameState.GAME;
import static game.GameState.MENU;

public class GameController implements MyKeyListener {
    private GameState gameState = GAME;
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

    public void updateGame() {
        if(gameState == GAME)
            updateModel();
        updateView();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void updateView() {
        gameView.render(gameModel, gameState);
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
                gameModel.getMenuModel().setPreviousItem();
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
                gameModel.getMenuModel().setNextItem();
                break;
            default:
        }
    }

    @Override
    public void enter() {
        switch(gameState) {
            case MENU:
                if(gameModel.getMenuModel().getSelected()== MenuItem.QUIT)
                    quitGame();
                else
                    startGame();
                break;
            default:
                break;
        }
    }

    @Override
    public void escape() {
        switch (gameState) {
            case GAME:
                stopGame();
                break;
            case MENU:
                quitGame();
                break;
            default:
        }
    }

    private void leftLane() {
        gameModel.getPlayerCar().setCarLane(CarLane.LEFT);
    }

    private void rightLane() {
        gameModel.getPlayerCar().setCarLane(CarLane.RIGHT);
    }

    private void accelerate() {
        gameModel.getPlayerCar().accelerate();
    }

    private void brake() {
        gameModel.getPlayerCar().brake();
    }

    private void startGame() {
        gameState = GAME;
    }

    private void stopGame() {
        gameState = MENU;
    }

    public void quitGame() {
        System.exit(0);
    }
}
