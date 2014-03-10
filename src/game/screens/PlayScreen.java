package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {
    
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Play Screen",2);
    }
    
    public Screen respondToUserInput(KeyEvent key) {
        return this;
    }
}
