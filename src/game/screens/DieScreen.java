package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class DieScreen implements Screen {

    public DieScreen() {
        
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You have died in vain; your noble quest remains unfinished.",2);
        terminal.writeCenter("Perhaps another adventurer will pick up where you left off.",3);
        terminal.writeCenter("--ENTER--",9);
    }
    
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
            return new StartScreen();
        
        return this;
    }
}
