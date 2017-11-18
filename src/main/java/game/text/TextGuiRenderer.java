package game.text;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import game.*;
import models.Menu;
import models.MenuItem;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class TextGuiRenderer extends GameView {

    private static int LINE_LENGTH = 4;

    private SwingTerminalFrame terminal;
    private VariableDistanceRenderer variableDistanceRenderer;
    private LineRenderer lineRenderer;

    public TextGuiRenderer(MyKeyListener keyListener) {
        super(keyListener);
    }

    private void registerListeners() {
        terminal.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println("key pressed");
                int keyCode = e.getKeyCode();
                switch (keyCode) {
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
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    @Override
    public void initialize() throws IOException, InterruptedException {
        terminal = GameTerminal.getInstance();
        terminal.setVisible(true);
        terminal.setBackgroundColor(new TextColor.RGB(0,0,0));
        terminal.setForegroundColor(new TextColor.RGB(255,255,255));
        terminal.setExtendedState(Frame.MAXIMIZED_BOTH);
        terminal.requestFocusInWindow();
        registerListeners();
        variableDistanceRenderer = new VariableDistanceRenderer(terminal);
//        lineRenderer = new LineRenderer(terminal, LINE_LENGTH);
        Thread.sleep(1000);
    }

    @Override
    public void render(GameModel model, GameState gameState) {
        terminal.setBackgroundColor(new TextColor.RGB(0, 0, 0));
        terminal.setForegroundColor(new TextColor.RGB(255, 255, 255));
//        System.out.println("rendering all");

        switch(gameState) {
            case GAME:
                try {
                    terminal.clearScreen();
                    variableDistanceRenderer.setTotalDistance(model.getCurrentRoute().getDistance());
                    renderDistance(model.getPlayerCar().getMetersFromStart(), model.getCurrentRoute().getDistance());
                    renderSpeedometer(model.getPlayerCar().getSpeed());
                    renderRoad(model);
                    variableDistanceRenderer.renderRoadSigns(model.getCurrentRoute().getRoadSigns());
                    variableDistanceRenderer.updateReferenceDistance(model.getPlayerCar().getMetersFromStart());
                    variableDistanceRenderer.renderPlayerCar(model.getPlayerCar());
                    variableDistanceRenderer.renderCars(model.getOtherCars());
//                    System.out.println(variableDistanceRenderer);
                    terminal.flush();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                break;
            case MENU:
                try {
                    terminal.clearScreen();
                    renderMenu(model.getMainMenu());
                    terminal.flush();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                break;
            case COLLISION:
                try {
                    renderMessage("GAME OVER!!!");
                    terminal.flush();
                    terminal.setForegroundColor(new TextColor.RGB(255,255,255));
                    renderMenu(model.getCollisionMenu());
                    terminal.flush();
                } catch(Exception exc) {
                    exc.printStackTrace();
                }
                break;
            case FINISHED:
                try {
                    renderMessage("TRACK COMPLETED!");
                    terminal.flush();
                    terminal.setForegroundColor(new TextColor.RGB(255,255,255));
                    renderMenu(model.getTrackCompletedMenu());
                    terminal.flush();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                break;
            case TRACKS:
                try {
                    terminal.clearScreen();
                    renderMenu(model.getTracksMenu());
                    terminal.flush();
                } catch(Exception exc) {
                    exc.printStackTrace();
                }
                break;
        }
    }

    private void renderDistance(int distance, int total) throws IOException, URISyntaxException {
        terminal.newTextGraphics().putString(
                new TerminalPosition(2,2), "distance: "+
                String.valueOf(distance/5) + "km/" + String.valueOf(total/5) + "km");
    }

    private void renderSpeedometer(int speed) {
        terminal.newTextGraphics().putString(new TerminalPosition(2,4), "speed: "+String.valueOf(speed*10) + "km/h");
    }

    private void renderMessage(String text) {
        int numColumns = howManyColumns();

        int currentRow = 1;
        int textWidth = text.length();

        terminal.setForegroundColor(new TextColor.RGB(255,0,0));
        terminal.newTextGraphics().putString(
                new TerminalPosition((numColumns-textWidth)/2, 1),
                text);
    }

    private void renderMenu(Menu menu) {
        int numColumns = howManyColumns();
        int numRows = howManyRows();

        int currentRow = 3;
        int menuwidth = numColumns/2;

        List<MenuItem> items = menu.getGameMenuItems();

        for(MenuItem menuItem: items) {
            if(menuItem == menu.getSelected())
                renderSelectedMenuItem(menuItem, currentRow, menuwidth);
            else
                renderMenuItem(menuItem, currentRow, menuwidth);
            currentRow += 3;
        }
    }

    private void renderSelectedMenuItem(MenuItem menuItem, int y, int minWidth) {
        int beginOn = (howManyColumns() - minWidth)/2;
//        System.out.println("begin="+beginOn);
        int endOn = (howManyColumns() + minWidth)/2;
//        System.out.println("end="+endOn);

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i, y);
            terminal.putCharacter('x');
        }

        terminal.setCursorPosition(beginOn, y+1);
        terminal.putCharacter('x');

        terminal.newTextGraphics().putString(new TerminalPosition(beginOn + 1, y+1), menuItem.getName());

        terminal.setCursorPosition(endOn, y+1);
        terminal.putCharacter('x');

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i,y+2);
            terminal.putCharacter('x');
        }

//        terminal.flush();
    }

    private void renderMenuItem(MenuItem menuItem, int y, int minWidth) {

//        System.out.println("COLUMNS="+howManyColumns());
//        System.out.println("ROWS="+howManyRows());
        int beginOn = (howManyColumns() - minWidth)/2;
//        System.out.println("begin="+beginOn);
        int endOn = (howManyColumns() + minWidth)/2;
//        System.out.println("end="+endOn);

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i, y);
            terminal.putCharacter('-');
        }

        terminal.setCursorPosition(beginOn, y+1);
        terminal.putCharacter('|');

        terminal.newTextGraphics().putString(new TerminalPosition(beginOn + 1, y+1), menuItem.getName());

        terminal.setCursorPosition(endOn, y+1);
        terminal.putCharacter('|');

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i,y+2);
            terminal.putCharacter('-');
        }

//        terminal.flush();
    }

    private void renderRoad(GameModel model) throws IOException {
        int numColumns = howManyColumns();
        int numRows = howManyRows();

        renderVerticalLine(numColumns/2 - 10);
        renderVerticalLine(numColumns/2 + 10);
        variableDistanceRenderer.renderInterruptedLine(model.getPlayerCar().getMetersFromStart());
//        renderInterruptedLine(numColumns/2);
//        lineRenderer.update(model.getPlayerCar().getSpeed()/8);
//        terminal.flush();
    }

    private void renderVerticalLine(int xPos) throws IOException {
        int numRows = howManyRows();

        for(int i=0; i!=numRows; ++i) {
            terminal.setCursorPosition(xPos, i);
            terminal.putCharacter('|');
        }
    }

//    private void renderInterruptedLine(int xPos) throws IOException {
//        int numRows = howManyRows();
//        int lenLine = 4;
//
//        lineRenderer.render(howManyColumns(), howManyRows());
//    }

    private int howManyColumns() {
        return terminal.getTerminalSize().getColumns();
    }

    private int howManyRows() {
        return terminal.getTerminalSize().getRows();
    }
}
