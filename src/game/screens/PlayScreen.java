package game.screens;

import asciiPanel.AsciiPanel;
import game.util.MathHelper;
import game.world.Area;
import game.world.Tile;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {
    
    public static final int MAP_OFFSET_X = 1;
    public static final int MAP_OFFSET_Y = 1;
    public static final int MAP_WIDTH = 50;
    public static final int MAP_HEIGHT = 16;
    
    private Area area;
    
    private int x = 0;
    private int y = 0;
    
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
                int camX = MathHelper.median(0,x-MAP_WIDTH/2,area.getWidth()-MAP_WIDTH);
                int camY = MathHelper.median(0,y-MAP_HEIGHT/2,area.getHeight()-MAP_WIDTH);
                terminal.write('@',x-camX+MAP_OFFSET_X,y-camY+MAP_OFFSET_Y,Color.WHITE);
                Tile t = area.getTile(i+camX,j+camY);
                terminal.write(t.glyph,i+MAP_OFFSET_X,j+MAP_OFFSET_Y,t.color);
            }
        }
    }
    
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_L)
            x++;
        else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_J)
            y++;
        else if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_H)
            x--;
        else if (k == KeyEvent.VK_UP || k == KeyEvent.VK_K)
            y--;
        else if (k == KeyEvent.VK_Y) {
            x--;
            y--;
        } else if (k == KeyEvent.VK_U) {
            x++;
            y--;
        } else if (k == KeyEvent.VK_B) {
            x--;
            y++;
        } else if (k == KeyEvent.VK_N) {
            x++;
            y++;
        }
        
        return this;
    }
}
