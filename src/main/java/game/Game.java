package game;

import java.io.IOException;

public class Game implements Runnable, MyExitListener {

    private boolean exit = false;
    private GameModel gameModel;
    private GameView renderer;
    private GameController gameController;

    public Game(GameModel gameModel, GameView gameView, GameController gameController) {
        this.gameModel = gameModel;
        this.renderer = gameView;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        System.out.println("running game");
        try {
            initializeView(renderer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(!exit) {
            gameController.updateGame();
            gameController.updateView();
            try {
                Thread.sleep(50);
            } catch(InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }

    @Override
    public void exit() {

    }

    private void initializeView(GameView gameView) throws IOException, InterruptedException {
        gameView.initialize();
    }
}
