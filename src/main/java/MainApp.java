import com.fasterxml.jackson.databind.ObjectMapper;
import game.*;
import game.gui.GraphicsGuiRenderer;
import game.text.TextGuiRenderer;
import models.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MainApp {

    public static void main(String[] args) {

        String filename = "track_names.json";
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        Scanner sc = new Scanner(is);
        sc.useDelimiter("\\Z");
        String data = sc.next();
//        System.out.println(data);
        ObjectMapper objectMapper = new ObjectMapper();
        String[] choices = null;
        try {
            choices = objectMapper.readValue(data, String[].class);
        } catch(IOException exc) {
            exc.printStackTrace();
        }

        List<TracksMenuItem> tracksMenuItems = new ArrayList<>();
        for(String choice: choices) {
            System.out.println(choice);
            tracksMenuItems.add(new TracksMenuItem(choice));
        }
//        System.out.println("from: "+route.getFrom());
//        System.out.println("to: "+route.getTo());
//        for(Map.Entry<Integer, String> sign: route.getRoadSigns().entrySet()) {
//            System.out.println(sign.getKey() + " km, welcome to " + sign.getValue());
//        }

        GameModel model = new GameModel(
                new MainMenu(Arrays.asList(MainMenuItem.NEW_GAME, MainMenuItem.QUIT)),
                new TracksMenu(tracksMenuItems),
                new CollisionMenu(Arrays.asList(CollisionMenuItem.GO_TO_MENU, CollisionMenuItem.TRY_AGAIN)),
                new TrackCompletedMenu(Arrays.asList(TrackCompletedMenuItem.GO_TO_MENU, TrackCompletedMenuItem.TRY_AGAIN)),
                new PlayerCar(20, CarColor.BLUE, CarLane.RIGHT),
                Arrays.asList()
                );
        GameController controller = GameController.getGameControllerInstance(model);

        GameView gameView = null;
        boolean graphics = true;
        if(graphics)
            gameView = new GraphicsGuiRenderer(controller);
        else
            gameView = new TextGuiRenderer(controller);

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
