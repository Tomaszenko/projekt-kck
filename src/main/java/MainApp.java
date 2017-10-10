import game.*;
import models.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MainApp {

    public static void main(String[] args) {

        GameModel model = new GameModel(
                new GameMenu(Arrays.asList(MenuItem.QUIT, MenuItem.PLAY)),
                new PlayerCar(50, CarColor.BLACK, CarLane.RIGHT),
                new ArrayList<>()
                );
        GameController controller = GameController.getGameControllerInstance(model);

        GameView gameView = new TextGuiRenderer(controller);

        controller.setGameView(gameView);

        Game game = new Game(model, gameView, controller);
        game.run();

//        try {
//            gameView.initialize();
//            System.out.println("abcd");
//        } catch(IOException exc) {
//            exc.printStackTrace();
//        }


//        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
//        Terminal terminal = null;
//
//        try {
//            terminal = defaultTerminalFactory.createTerminal();
//            terminal.setCursorVisible(false);
//
//            setBackgroundColor(TextColor.ANSI.BLUE, terminal);
//            terminal.setForegroundColor(TextColor.ANSI.YELLOW);
//
//            TextGraphics graphics = terminal.newTextGraphics();
//
//
//            System.out.println(terminal.getTerminalSize().getColumns() + "x" + terminal.getTerminalSize().getRows());
//            terminal.setCursorPosition(0,0);
//            terminal.putCharacter('H');
//            terminal.putCharacter('e');
//            terminal.putCharacter('l');
//            terminal.putCharacter('l');
//            terminal.putCharacter('o');
//            terminal.putCharacter('\n');
//            terminal.flush();
//        } catch(IOException exc) {
//            exc.printStackTrace();
//        }
    }

//    public static void setBackgroundColor(TextColor textColor, Terminal terminal) throws IOException {
//        terminal.setBackgroundColor(textColor);
//        for(int i=0; i!=terminal.getTerminalSize().getRows(); ++i) {
//            for(int j=0; j!=terminal.getTerminalSize().getColumns(); ++j) {
//                terminal.putCharacter(' ');
//            }
//        }
//    }
}
