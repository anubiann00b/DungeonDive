package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class StartScreen implements Screen {
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You are an adventurer.",2);
        terminal.writeCenter("The lord of your realm offers a thousand gold coins to",3);
        terminal.writeCenter("the man who can kill all the orcs and save the land from",4);
        terminal.writeCenter("the orcish pestilence. You have accepted this quest, and",5);
        terminal.writeCenter("now you must kill the orcs, or die trying.",6);
        terminal.writeCenter("--ENTER--",12);
    }
    
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
            return new PlayScreen(20);
        
        return this;
    }
}
