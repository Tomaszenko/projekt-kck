package game.gui;

import game.GameModel;
import game.GameState;
import game.GameView;
import game.MyKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GraphicsGuiRenderer extends GameView {
    private MyGameFrame gameFrame;
    private Robot robot;
//    private Stage stage;

    private GameState previousState = null;

    private Set<Integer> events = new HashSet<>();

    public GraphicsGuiRenderer(MyKeyListener keyListener) {
        super(keyListener);
    }

    private void registerListeners() {
        gameFrame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("System key pressed");
                events.add(e.getKeyCode());

                for(Integer event: events) {
                    switch(event) {
                        case KeyEvent.VK_UP:
                            getKeyListener().up();
                            break;
                        case KeyEvent.VK_DOWN:
                            getKeyListener().down();
                            break;
                        case KeyEvent.VK_LEFT:
                            getKeyListener().left();
                            break;
                        case KeyEvent.VK_RIGHT:
                            getKeyListener().right();
                            break;
                        case KeyEvent.VK_ESCAPE:
                            getKeyListener().escape();
                            break;
                        case KeyEvent.VK_ENTER:
                            getKeyListener().enter();
                            break;
                        case KeyEvent.VK_SPACE:
                            getKeyListener().space();
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("System key released");
                events.remove(e.getKeyCode());
            }
        });
    }

    @Override
    public void initialize() throws IOException, InterruptedException {
        gameFrame = new MyGameFrame("Hello"); // construct a MyFrame object
        gameFrame.setVisible( true );
        gameFrame.requestFocusInWindow();
        registerListeners();
    }

    @Override
    public void render(GameModel model, GameState gameState) {
        System.out.println("We are here");

        long start = System.currentTimeMillis();

        switch(gameState) {
            case MENU:
                if(previousState != GameState.MENU) {
                    gameFrame.drawMainMenu(model.getMainMenu());
                    gameFrame.revalidate();
                }
                previousState = GameState.MENU;
                break;
            case TRACKS:
                if(previousState != GameState.TRACKS) {
                    gameFrame.drawTracksMenu(model.getTracksMenu());
                }
                previousState = GameState.TRACKS;
                break;
            case GAME:
                if(previousState != GameState.GAME) {
                    gameFrame.drawGame(model);
                } else {
                    gameFrame.updateGame(model);
                }
                previousState = GameState.GAME;
                break;
            case COLLISION:
                if(previousState != GameState.COLLISION) {
                    gameFrame.gameOver();
                }
                previousState = GameState.COLLISION;
                break;
            case FINISHED:
                if(previousState != GameState.FINISHED) {
                    gameFrame.trackCompleted();
                }
                previousState = GameState.FINISHED;
                break;
        }
        long end = System.currentTimeMillis();
        System.out.println("WIDOK: "+String.valueOf(end-start) + "ms");
    }
}
