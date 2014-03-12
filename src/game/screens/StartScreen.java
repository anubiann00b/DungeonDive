package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class StartScreen implements Screen {
    
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("Dungeon Dive",2);
    }
    
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
            return new PlayScreen(20);
        
        return this;
    }
}
