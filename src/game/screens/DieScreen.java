package game.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class DieScreen implements Screen {

    private int steps;
    private int tiles;
    private int enemies;
    private long time;
    
    public DieScreen(int steps, int tiles, int enemies, long time) {
        this.steps = steps;
        this.tiles = tiles;
        this.enemies = enemies;
        this.time = time;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("You have died in vain; your noble quest remains unfinished.",2);
        terminal.writeCenter("Perhaps another adventurer will pick up where you left off.",3);
        
        terminal.writeCenter("Steps: " + steps,5);
        terminal.writeCenter("Tiles: " + tiles,6);
        terminal.writeCenter("Enemies: " + enemies,7);
        
        String timeString = String.format("%02d:%02d", 
            TimeUnit.MILLISECONDS.toMinutes(time),
            TimeUnit.MILLISECONDS.toSeconds(time) - 
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
        
        terminal.writeCenter("Time: " + timeString,9);
        
        terminal.writeCenter("--ENTER--",14);
    }
    
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_ENTER)
            return new StartScreen();
        
        return this;
    }
}
