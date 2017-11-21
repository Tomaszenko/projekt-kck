package game;

import java.io.IOException;

public class Game implements Runnable, MyExitListener {

    private boolean exit = false;
    private boolean stopped = false;
    private GameModel gameModel;
    private GameView renderer;
    private GameController gameController;

    public Game(GameModel gameModel, GameView gameView, GameController gameController) {
        this.gameModel = gameModel;
        this.renderer = gameView;
        this.gameController = gameController;
        gameModel.setCollisionListener(gameController);
        gameModel.setFinishListener(gameController);
    }

    @Override
    public void run() {
//        System.out.println("running game");
        try {
            initializeView(renderer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        while(!exit) {
            long start = System.currentTimeMillis();
            gameController.updateGame();
//            System.out.println("before update");
//            gameController.updateView();
//            System.out.println("after update");
            try {
                Thread.sleep(50);
            } catch(InterruptedException exc) {
                exc.printStackTrace();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start + " ms");
        }
    }

    @Override
    public void exit() {
        exit = true;
    }

    private void initializeView(GameView gameView) throws IOException, InterruptedException {
        gameView.initialize();
    }
}
