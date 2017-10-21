package game;

import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

public class LineRenderer {
    private int stripeStart = 0;
    private int lineLength;
    private SwingTerminalFrame terminal;

    public LineRenderer(SwingTerminalFrame terminal, int lineLength) {
        this.terminal = terminal;
        this.lineLength = lineLength;
    }

    public void update(int playerSpeed) {
        stripeStart = (stripeStart + playerSpeed)%(2*lineLength);
    }

    public void render(int numCols, int numRows) {
        for(int i=stripeStart; i!=numRows; ++i) {
            terminal.setCursorPosition((int)(numCols/2), i);
            if((i-stripeStart)/lineLength%2 == 0)
                terminal.putCharacter('|');
        }

        if(stripeStart > lineLength) {
            for(int i=0; i!=(stripeStart-lineLength); ++i) {
                terminal.setCursorPosition((int)(numCols/2), i);
                terminal.putCharacter('|');
            }
        }
    }
}
