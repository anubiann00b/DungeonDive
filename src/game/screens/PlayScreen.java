package game.screens;

import asciiPanel.AsciiPanel;
import game.world.Area;
import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {
    
    private Area currentArea;
    
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Play Screen",2);
    }
    
    public Screen respondToUserInput(KeyEvent key) {
        return this;
    }
}
