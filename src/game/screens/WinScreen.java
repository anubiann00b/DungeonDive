package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class WinScreen implements Screen {
    
    private int time;
    private int tiles;
    private int enemies;
    
    public WinScreen(int time, int tiles, int enemies) {
        this.time = time;
        this.tiles = tiles;
        this.enemies = enemies;
    }
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You have cleared the infestation of orcs!",2);
        terminal.writeCenter("Time: " + time,4);
        terminal.writeCenter("Tiles: " + tiles,5);
        terminal.writeCenter("Enemies: " + enemies,6);
        terminal.writeCenter("--ENTER--",12);
    }
    
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
            return new StartScreen();
        
        return this;
    }
}
