package game.screens;

import asciiPanel.AsciiPanel;
import game.util.FieldOfView;
import game.util.MathHelper;
import game.util.Message;
import game.world.Tile;
import game.world.World;
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
    
    public static int level = 0;
    
    private World world;
    
    private FieldOfView fov;
    
    private int x;
    private int y;
    
    private Stack<Message> messages;
    
    public PlayScreen() {
        level++;
        world = new World(MAP_WIDTH,MAP_HEIGHT,level);
        do {
            x = (int)(Math.random()*MAP_WIDTH);
            y = (int)(Math.random()*MAP_HEIGHT);
        } while (!world.getTile(x,y).isPassable);
        messages = new Stack<Message>();
        fov = new FieldOfView(world);
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
        fov.update(x,y,9);
        for(int i=0;i<MAP_WINDOW_X;i++) {
            for (int j=0;j<MAP_WINDOW_Y;j++) {
                int camX = MathHelper.median(0,x-MAP_WINDOW_X/2,MAP_WIDTH-MAP_WINDOW_X);
                int camY = MathHelper.median(0,y-MAP_WINDOW_Y/2,MAP_HEIGHT-MAP_WINDOW_Y);
                
                terminal.write('@',x-camX+MAP_OFFSET_X,y-camY+MAP_OFFSET_Y,Color.WHITE);
                
                Tile tile = world.getTile(i+camX,j+camY);
                
                if (fov.isVisible(i+camX,j+camY))
                    terminal.write(tile.glyph,i+MAP_OFFSET_X,j+MAP_OFFSET_Y,tile.color);
                else {
                    tile = fov.tile(i+camX,j+camY);
                    terminal.write(tile.glyph,i+MAP_OFFSET_X,j+MAP_OFFSET_Y,Color.DARK_GRAY);
                }
            }
        }
    }
    
    private void moveBy(int dx, int dy) {
        if (world.getTile(x+dx,y+dy).isPassable) {
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
        boolean updated = true;
        int k = key.getKeyCode();
        
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_L || k == KeyEvent.VK_NUMPAD6)
            moveBy(1,0);
        else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_J || k == KeyEvent.VK_NUMPAD2)
            moveBy(0,1);
        else if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_H || k == KeyEvent.VK_NUMPAD4)
            moveBy(-1,0);
        else if (k == KeyEvent.VK_UP || k == KeyEvent.VK_K || k == KeyEvent.VK_NUMPAD8)
            moveBy(0,-1);
        else if (k == KeyEvent.VK_Y || k == KeyEvent.VK_NUMPAD7)
            moveBy(-1,-1);
        else if (k == KeyEvent.VK_U || k == KeyEvent.VK_NUMPAD9)
            moveBy(1,-1);
        else if (k == KeyEvent.VK_B || k == KeyEvent.VK_NUMPAD1)
            moveBy(-1,1);
        else if (k == KeyEvent.VK_N || k == KeyEvent.VK_NUMPAD3)
            moveBy(1,1);
        else
            updated = false;
        
        return this;
    }
}
