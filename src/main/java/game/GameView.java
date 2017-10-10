package game;

import java.io.IOException;

public abstract class GameView {
    private MyKeyListener keyListener;

    public GameView(MyKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    protected MyKeyListener getKeyListener() {
        return keyListener;
    }

    public void setKeyListener(MyKeyListener listener) {
        this.keyListener = listener;
    }

    public abstract void initialize() throws IOException, InterruptedException;
    public abstract void render(GameModel model, GameState gameState);
}
