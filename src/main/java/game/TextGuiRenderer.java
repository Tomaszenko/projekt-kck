package game;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.Charset;

public class TextGuiRenderer extends GameView {

    private Terminal ansiTerminal;

    public void keyPressed() throws IOException {
        KeyboardInputListener keyboardInputListener = new KeyboardInputListener(ansiTerminal);
        keyboardInputListener.run();
    }

//    private List<ViewListener> listeners = new ArrayList<ViewListener>();
//
//    public void addListener(ViewListener viewListener) {
//        listeners.add(viewListener);
//    }
//
//    // Someone who says "Hello"
//    class Initiater {
//        private List<HelloListener> listeners = new ArrayList<HelloListener>();
//
//        public void addListener(HelloListener toAdd) {
//            listeners.add(toAdd);
//        }
//
//        public void sayHello() {
//            System.out.println("Hello!!");
//
//            // Notify everybody that may be interested.
//            for (HelloListener hl : listeners)
//                hl.someoneSaidHello();
//        }
//    }
//
//    // Someone interested in "Hello" events
//    class Responder implements HelloListener {
//        @Override
//        public void someoneSaidHello() {
//            System.out.println("Hello there...");
//        }
//    }

    @Override
    public void initialize() throws IOException {
        ansiTerminal = new DefaultTerminalFactory(
                System.out, System.in, Charset.forName("UTF8")
        ).createTerminal();

        keyPressed();
    }

    @Override
    public void render(GameModel g) {

    }
}
