package game;

import game.GameModel;

import java.io.IOException;

public abstract class GameView {

    private GameModel gameModel;


    public abstract void initialize() throws IOException;
    public abstract void render(GameModel g);
}
