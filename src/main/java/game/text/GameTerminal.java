package game.text;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.ansi.ANSITerminal;
import com.googlecode.lanterna.terminal.ansi.FixedTerminalSizeProvider;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import com.googlecode.lanterna.terminal.swing.TerminalEmulatorAutoCloseTrigger;
import sun.awt.X11.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.Charset;

public class GameTerminal {
    private static SwingTerminalFrame terminal;

    private GameTerminal() {
    }

    public static SwingTerminalFrame getInstance() throws IOException {
        if(terminal == null) {
            terminal = new SwingTerminalFrame("abcd", TerminalEmulatorAutoCloseTrigger.DoNotAutoClose);
            terminal.setVisible(true);
            terminal.setSize(new Dimension(600,600));
            System.out.println("created");
            terminal.setFocusable(true);
            terminal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            terminal.setCursorVisible(false);
        }

        return terminal;
    }
}
