package game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.ansi.ANSITerminal;

import java.io.IOException;

public class KeyboardInputListener implements Runnable {

    private Terminal ansiTerminal;

    public KeyboardInputListener(Terminal ansiTerminal) {
        this.ansiTerminal = ansiTerminal;
    }

    public void run() {
        while(true) {

            KeyStroke keyStroke = null;
            try {
                keyStroke = ansiTerminal.pollInput();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (keyStroke != null) {
                System.out.println("key stroked");
                KeyType keyType = keyStroke.getKeyType();
                if (keyType != null) {
                    try {
                        ansiTerminal.putCharacter('a');
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
