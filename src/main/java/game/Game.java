package game;

import java.io.IOException;

public class Game implements Runnable {
    private GameModel gameModel;
    private GameView renderer;
    private GameController controller;

    public Game(GameModel gameModel, GameView renderer, GameController controller) {
        this.gameModel = gameModel;
        this.renderer = renderer;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            initializeView(renderer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            renderer.render(gameModel);
            updateModel();
        }
    }

    private void initializeView(GameView gameView) throws IOException{
        gameView.initialize();
    }
    private void updateModel() {
        gameModel.update();
    }
}
