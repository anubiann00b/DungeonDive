package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WinScreen implements Screen {
    
    private int steps;
    private int tiles;
    private int enemies;
    private long time;
    
    public WinScreen(int steps, int tiles, int enemies, long time) {
        this.steps = steps;
        this.tiles = tiles;
        this.enemies = enemies;
        this.time = time;
    }
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You have cleared the infestation of orcs!",2);
        
        terminal.writeCenter("Steps: " + steps,4);
        terminal.writeCenter("Tiles: " + tiles,5);
        terminal.writeCenter("Enemies: " + enemies,6);
        
        String timeString = String.format("%02d:%02d", 
            TimeUnit.MILLISECONDS.toMinutes(time),
            TimeUnit.MILLISECONDS.toSeconds(time) - 
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
        
        terminal.writeCenter("Time: " + timeString,8);
        
        terminal.writeCenter("--ENTER--",13);
    }
    
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
            return new StartScreen();
        
        return this;
    }
}
