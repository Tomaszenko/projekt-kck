package game;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import models.CarLane;
import models.GameMenu;
import models.MenuItem;
import models.PlayerCar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;

public class TextGuiRenderer extends GameView {

    private SwingTerminalFrame terminal;

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
                System.out.println("key pressed");
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
        terminal.requestFocusInWindow();
        registerListeners();
        Thread.sleep(1000);
    }

    @Override
    public void render(GameModel model, GameState gameState) {
        terminal.setBackgroundColor(new TextColor.RGB(0, 0, 0));
        terminal.setForegroundColor(new TextColor.RGB(255, 255, 255));
        terminal.clearScreen();

        if(gameState == GameState.GAME) {
            try {
                renderRoad();
                renderCar(model.getPlayerCar());
            } catch (Exception exc) {
                System.out.println("ups sorki");
            }
        } else {
            if(gameState == GameState.MENU) {
                try {
                    renderMenu(model.getMenuModel());
                } catch (Exception exc) {
                    System.out.println("ups sorki, wyświetlam menu");
                }

            }
        }
    }

    private void renderMenu(GameMenu gameMenu) {
        int numColumns = howManyColumns();
        int numRows = howManyRows();

        int currentRow = 1;
        int menuwidth = numColumns/2;

        List<MenuItem> items = gameMenu.getGameMenuItems();

        for(MenuItem menuItem: items) {
            if(menuItem == gameMenu.getSelected())
                renderSelectedMenuItem(menuItem, currentRow, menuwidth);
            else
                renderMenuItem(menuItem, currentRow, menuwidth);
            currentRow += 3;
        }
    }

    private void renderSelectedMenuItem(MenuItem menuItem, int y, int minWidth) {
        int beginOn = (howManyColumns() - minWidth)/2;
        System.out.println("begin="+beginOn);
        int endOn = (howManyColumns() + minWidth)/2;
        System.out.println("end="+endOn);

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i, y);
            terminal.putCharacter('x');
        }

        terminal.setCursorPosition(beginOn, y+1);
        terminal.putCharacter('x');

        terminal.newTextGraphics().putString(new TerminalPosition(beginOn + 1, y+1), menuItem.name());

        terminal.setCursorPosition(endOn, y+1);
        terminal.putCharacter('x');

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i,y+2);
            terminal.putCharacter('x');
        }

        terminal.flush();
    }

    private void renderMenuItem(MenuItem menuItem, int y, int minWidth) {

        System.out.println("COLUMNS="+howManyColumns());
        int beginOn = (howManyColumns() - minWidth)/2;
        System.out.println("begin="+beginOn);
        int endOn = (howManyColumns() + minWidth)/2;
        System.out.println("end="+endOn);

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i, y);
            terminal.putCharacter('-');
        }

        terminal.setCursorPosition(beginOn, y+1);
        terminal.putCharacter('|');

        terminal.newTextGraphics().putString(new TerminalPosition(beginOn + 1, y+1), menuItem.name());

        terminal.setCursorPosition(endOn, y+1);
        terminal.putCharacter('|');

        for(int i=beginOn; i<=endOn; ++i) {
            terminal.setCursorPosition(i,y+2);
            terminal.putCharacter('-');
        }

        terminal.flush();
    }

    private void renderCar(PlayerCar car) throws IOException {
        System.out.println("rendering car");
        int numColumns = howManyColumns();
        int numRows = howManyRows();

        if(car.getCarLane()== CarLane.LEFT)
            renderPlayerCar(numColumns/2 - 5,numRows - 4);
        else
            renderPlayerCar(numColumns/2 + 5,numRows - 4);
    }

    private void renderRoad() throws IOException {
        int numColumns = howManyColumns();
        int numRows = howManyRows();

        renderVerticalLine(numColumns/2 - 10);
        renderVerticalLine(numColumns/2 + 10);
        renderInterruptedLine(numColumns/2);
        terminal.flush();
    }

    private void renderVerticalLine(int xPos) throws IOException {
        int numRows = howManyRows();

        for(int i=0; i!=numRows; ++i) {
            terminal.setCursorPosition(xPos, i);
            terminal.putCharacter('|');
        }
    }

    private void renderInterruptedLine(int xPos) throws IOException {
        int numRows = howManyRows();
        int lenLine = 4;

        for(int i=0; i!=numRows; ++i) {
            terminal.setCursorPosition(xPos, i);
            if(i/lenLine%2 == 0)
                terminal.putCharacter('|');
        }
    }

    private void renderPlayerCar(int x, int y) throws IOException {
        terminal.setCursorPosition(x-1, y);
        terminal.putCharacter('[');
        terminal.setCursorPosition(x, y);
        terminal.putCharacter('^');
        terminal.setCursorPosition(x+1, y);
        terminal.putCharacter(']');
        terminal.setCursorPosition(x, y+1);
        terminal.putCharacter('I');
        terminal.setCursorPosition(x, y+2);
        terminal.putCharacter('O');
        terminal.setCursorPosition(x-1, y+3);
        terminal.putCharacter('[');
        terminal.setCursorPosition(x, y+3);
        terminal.putCharacter('-');
        terminal.setCursorPosition(x+1, y+3);
        terminal.putCharacter(']');
        terminal.flush();
        System.out.println("out from rendering car");
    }

    private int howManyColumns() {
        return terminal.getTerminalSize().getColumns();
    }

    private int howManyRows() {
        return terminal.getTerminalSize().getRows();
    }


}
