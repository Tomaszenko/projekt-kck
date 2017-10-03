package game;

import com.googlecode.lanterna.gui2.TextGUI;
import com.googlecode.lanterna.input.KeyStroke;

public class GameController implements TextGUI.Listener {

    private GameModel gameModel;

    private static GameController gameController = null;

    public static GameController getGameControllerInstance(GameModel gameModel) {
        if(gameController == null)
            gameController = new GameController(gameModel);
        return gameController;
    }

    private GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void onKeyUp() {
        this.gameModel.getPlayerCar().setCarDirection(CarDirection.NORTH);
    }

    public void onKeyDown() {
        this.gameModel.getPlayerCar().setCarDirection(CarDirection.SOUTH);
    }

    public void onKeyLeft() {
        this.gameModel.getPlayerCar().setCarDirection(CarDirection.WEST);
    }

    public void onKeyRight() {
        this.gameModel.getPlayerCar().setCarDirection(CarDirection.EAST);
    }

    @Override
    public boolean onUnhandledKeyStroke(TextGUI textGUI, KeyStroke keyStroke) {
        return false;
    }
}
