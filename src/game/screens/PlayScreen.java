package game.screens;

import asciiPanel.AsciiPanel;
import game.enemy.Enemy;
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
    public static final int MAP_WINDOW_X = 40;
    public static final int MAP_WINDOW_Y = 16;
    public static final int MAP_WIDTH = 180;
    public static final int MAP_HEIGHT = 124;
    public static final int MESSAGE_WINDOW_POS = 17;
    public static final int MESSAGE_WINDOW_SIZE = 7;
    
    public static int level = 0;
    
    private World world;
    
    private FieldOfView fov;
    
    private int px;
    private int py;
    private int totalHp;
    private int currentHp;
    private int pAC;
    
    private Stack<Message> messages;
    
    public int getPlayerX() { return px; }
    public int getPlayerY() { return py; }
    
    public int getPlayerAC() { return pAC; }
    
    public void damage(int amount) { if(currentHp<amount) currentHp -= amount; else currentHp = 0; }
    
    public World getWorld() { return world; }
    
    public void addMessage(Message message) {
        messages.push(message);
    }
    
    public PlayScreen() {
        pAC = 10;
        totalHp = 50;
        currentHp = totalHp;
        level++;
        world = new World(MAP_WIDTH,MAP_HEIGHT,level,this);
        do {
            px = (int)(Math.random()*MAP_WIDTH);
            py = (int)(Math.random()*MAP_HEIGHT);
        } while (!world.getTile(px,py).isPassable);
        messages = new Stack<Message>();
        fov = new FieldOfView(world);
        
        for (int i=0;i<50;i++) {
            world.addEnemy();
        }
    }
    
    public void displayOutput(AsciiPanel terminal) {
        drawMap(terminal);
        drawMessages(terminal);
        drawInfo(terminal);
    }
    
    private void drawInfo(AsciiPanel terminal) {
        terminal.write("Dungeon Dive",MAP_OFFSET_X+MAP_WINDOW_X,MAP_OFFSET_Y);
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
        fov.update(px,py,9);
        
        int camX = MathHelper.median(0,px-MAP_WINDOW_X/2,MAP_WIDTH-MAP_WINDOW_X);
        int camY = MathHelper.median(0,py-MAP_WINDOW_Y/2,MAP_HEIGHT-MAP_WINDOW_Y);
        
        for(int i=0;i<MAP_WINDOW_X;i++) {
            for (int j=0;j<MAP_WINDOW_Y;j++) {
                Tile tile = world.getTile(i+camX,j+camY);
                
                if (fov.isVisible(i+camX,j+camY))
                    terminal.write(tile.glyph,i+MAP_OFFSET_X,j+MAP_OFFSET_Y,tile.color);
                else {
                    tile = fov.tile(i+camX,j+camY);
                    terminal.write(tile.glyph,i+MAP_OFFSET_X,j+MAP_OFFSET_Y,Color.DARK_GRAY);
                }
            }
        }
        
        for (Enemy e : world.getEnemies()) {
            if (fov.isVisible(e.getX(),e.getY()))
                terminal.write(e.getGlyph(),e.getX()-camX+MAP_OFFSET_X,e.getY()-camY+MAP_OFFSET_Y,e.getColor());
        }
        terminal.write('@',px-camX+MAP_OFFSET_X,py-camY+MAP_OFFSET_Y,Color.WHITE);
    }
    
    private void moveBy(int dx, int dy) {
        Enemy e = world.getEnemy(px+dx,py+dy);
        
        if (e != null) {
            attack(e);
            return;
        }
        
        if (world.getTile(px+dx,py+dy).isPassable) {
            px += dx;
            py += dy;
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
    
    public void attack(Enemy e) {
        String message = "You hit the " + e.getName() + ".";
        addMessage(new Message(message,Color.YELLOW));
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
        else if (k == KeyEvent.VK_PERIOD || k == KeyEvent.VK_S)
            addMessage(new Message("You wait.",AsciiPanel.white));
        else if (k == KeyEvent.VK_SLASH && key.isShiftDown())
            return new HelpScreen(this);
        else
            updated = false;
        
        if (updated)
            world.update();
        
        return this;
    }
}
