package game.screens;

import asciiPanel.AsciiPanel;
import game.world.Area;
import game.world.Tile;
import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {
    
    public static final int MAP_WIDTH = 80;
    public static final int MAP_HEIGHT = 24;
    
    private Area area;
    
    private int camX = 0;
    private int camY = 0;
    
    public PlayScreen() {
        init();
    }
    
    private void init() {
        area = new Area(180,124,Area.TYPE_CAVE);
    }
    
    public void displayOutput(AsciiPanel terminal) {
        drawMap(terminal);
    }
    
    private void drawMap(AsciiPanel terminal) {
        for(int i=0;i<MAP_WIDTH;i++) {
            for (int j=0;j<MAP_HEIGHT;j++) {
                Tile t = area.getTile(i+camX,j+camY);
                terminal.write(t.glyph,i,j,t.color);
            }
        }
    }
    
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_L)
            camX++;
        else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_J)
            camY++;
        else if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_H)
            camX--;
        else if (k == KeyEvent.VK_UP || k == KeyEvent.VK_K)
            camY--;
        else if (k == KeyEvent.VK_Y) {
            camX--;
            camY--;
        } else if (k == KeyEvent.VK_U) {
            camX++;
            camY--;
        } else if (k == KeyEvent.VK_B) {
            camX--;
            camY++;
        } else if (k == KeyEvent.VK_N) {
            camX++;
            camY++;
        }
            
        
        return this;
    }
}
