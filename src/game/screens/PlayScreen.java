package game.screens;

import asciiPanel.AsciiPanel;
import game.util.MathHelper;
import game.util.Message;
import game.world.Area;
import game.world.Tile;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class PlayScreen implements Screen {
    
    public static final int MAP_OFFSET_X = 1;
    public static final int MAP_OFFSET_Y = 1;
    public static final int MAP_WINDOW_X = 50;
    public static final int MAP_WINDOW_Y = 16;
    public static final int MAP_WIDTH = 180;
    public static final int MAP_HEIGHT = 124;
    public static final int MESSAGE_WINDOW_POS = 17;
    public static final int MESSAGE_WINDOW_SIZE = 7;
    
    private Area area;
    
    private int x;
    private int y;
    
    private Stack<Message> messages;
    
    public PlayScreen() {
        init();
        messages = new Stack<Message>();
    }
    
    private void init() {
        area = new Area(MAP_WIDTH,MAP_HEIGHT,Area.TYPE_CAVE);
        do {
            x = (int)(Math.random()*MAP_WIDTH);
            y = (int)(Math.random()*MAP_HEIGHT);
        } while (!area.getTile(x,y).isPassable);
        System.out.println(x + " " + y);
    }
    
    public void displayOutput(AsciiPanel terminal) {
        drawMap(terminal);
        drawMessages(terminal);
    }
    
    private void drawMessages(AsciiPanel terminal) {
        for (int i=0;i<MESSAGE_WINDOW_SIZE;i++) {
            if(messages.empty())
                return;
            Message message = messages.pop();
            terminal.write(message.message,1,MESSAGE_WINDOW_POS+i,message.color);
        }
    }
    
    private void drawMap(AsciiPanel terminal) {
        for(int i=0;i<MAP_WINDOW_X;i++) {
            for (int j=0;j<MAP_WINDOW_Y;j++) {
                int camX = MathHelper.median(0,x-MAP_WINDOW_X/2,MAP_WIDTH-MAP_WINDOW_X);
                int camY = MathHelper.median(0,y-MAP_WINDOW_Y/2,MAP_HEIGHT-MAP_WINDOW_Y);
                terminal.write('@',x-camX+MAP_OFFSET_X,y-camY+MAP_OFFSET_Y,Color.WHITE);
                Tile tile = area.getTile(i+camX,j+camY);
                terminal.write(tile.glyph,i+MAP_OFFSET_X,j+MAP_OFFSET_Y,tile.color);
            }
        }
    }
    
    private void moveBy(int dx, int dy) {
        if (area.getTile(x+dx,y+dy).isPassable) {
            x += dx;
            y += dy;
            return;
        }
        String message;
        switch ((int)(Math.random()*5)) {
            case 0:
                message = "Good luck trying to walk through walls.";
                break;
            case 1:
                message = "You can't move there.";
                break;
            case 2:
                message = "You crash into a stone wall.";
                break;
            case 3:
                message = "Ow.";
                break;
            default:
                message = "The wall mutters some questionable adjectives.";
                break;
        }
        addMessage(new Message(message,Color.ORANGE));
    }
    
    private void addMessage(Message message) {
        messages.push(message);
    }
    
    public Screen respondToUserInput(KeyEvent key) {
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_L)
            moveBy(1,0);
        else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_J)
            moveBy(0,1);
        else if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_H)
            moveBy(-1,0);
        else if (k == KeyEvent.VK_UP || k == KeyEvent.VK_K)
            moveBy(0,-1);
        else if (k == KeyEvent.VK_Y)
            moveBy(-1,-1);
        else if (k == KeyEvent.VK_U)
            moveBy(1,-1);
        else if (k == KeyEvent.VK_B)
            moveBy(-1,1);
        else if (k == KeyEvent.VK_N)
            moveBy(1,1);
        
        return this;
    }
}
