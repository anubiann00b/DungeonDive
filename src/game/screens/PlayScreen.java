package game.screens;

import asciiPanel.AsciiPanel;
import game.enemy.Enemy;
import game.util.Dice;
import game.util.FieldOfView;
import game.util.MathHelper;
import game.util.Message;
import game.world.Tile;
import game.world.World;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

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
    
    public final int MAX_ENEMIES = 100;
    
    private World world;
    
    private FieldOfView fov;
    
    private int px;
    private int py;
    private int pAC;
    
    private int damageDie;
    private int damageBonus;
    private int attackBonus;
    
    private int totalHP;
    private int currentHP;
    
    private int totalStepHP = 10;
    private int currentStepHP = 0;
    
    private int quaffFC = 8;
    private int totalFC = 16;
    private int currentFC = totalFC;
    
    private int totalStepFC = 8;
    private int currentStepFC = 0;
    
    private int time = 0;
    
    private Queue<Message> messages;
    
    public int getPlayerX() { return px; }
    public int getPlayerY() { return py; }
    
    public int getPlayerAC() { return pAC; }
    
    public void damage(int amount) {
        if(currentHP>amount)
            currentHP -= amount;
        else
            currentHP = 0;
        currentStepHP = 0;
    }
    
    public void heal(int amount) {
        if(currentHP+amount<totalHP)
            currentHP += amount;
        else
            currentHP = totalHP;
        currentStepHP = 0;
    }
    
    public void regenFlask(int amount) {
        if(currentFC+amount<totalFC)
            currentFC += amount;
        else
            currentFC = totalFC;
        currentStepFC = 0;
    }
    
    public World getWorld() { return world; }
    
    public void addMessage(Message message) {
        messages.offer(message);
    }
    
    public PlayScreen(int hp) {
        pAC = 14;
        damageDie = 6;
        damageBonus = 2;
        attackBonus = 3;
        totalHP = hp;
        
        currentHP = totalHP;
        level++;
        world = new World(MAP_WIDTH,MAP_HEIGHT,level,this);
        do {
            px = (int)(Math.random()*MAP_WIDTH);
            py = (int)(Math.random()*MAP_HEIGHT);
        } while (!world.getTile(px,py).isPassable);
        messages = new LinkedList<Message>();
        fov = new FieldOfView(world);
        
        for (int i=0;i<MAX_ENEMIES;i++) {
            world.addEnemy();
        }
    }
    
    @Override
    public void displayOutput(AsciiPanel terminal) {
        drawMap(terminal);
        drawMessages(terminal);
        drawInfo(terminal);
    }
    
    private void drawInfo(AsciiPanel terminal) {
        writeInfo("Dungeon Dive",0,0,terminal);
        
        writeInfo("HP: " + currentHP + "/" + totalHP,0,2,terminal);
        
        int hBars = 28*currentHP/totalHP;
        
        for (int i=0;i<hBars;i++)
            writeInfo("=",10+i,2,Color.GREEN,terminal);
        
        for (int i=0;i<28-hBars;i++)
            writeInfo("-",10+hBars+i,2,terminal);
        
        writeInfo("FC: " + currentFC + "/" + totalFC,0,3,terminal);
        
        int fBars = 28*currentFC/totalFC;
        
        for (int i=0;i<fBars;i++)
            writeInfo("=",10+i,3,Color.BLUE,terminal);
        
        for (int i=0;i<28-fBars;i++)
            writeInfo("-",10+fBars+i,3,terminal);
        
        int enemies = world.getNumEnemies();
        writeInfo("Enemies remaining: " + enemies,0,5,terminal);
        
        int tiles = fov.getExploredTiles();
        writeInfo("Tiles explored: " + tiles,0,6,terminal);
    }
    
    private void writeInfo(String text, int x, int y, AsciiPanel terminal) {
        writeInfo(text,x,y,AsciiPanel.white,terminal);
    }
    
    private void writeInfo(String text, int x, int y, Color color, AsciiPanel terminal) {
        terminal.write(text,x+MAP_OFFSET_X+MAP_WINDOW_X,y+MAP_OFFSET_Y,color);
    }
    
    private void drawMessages(AsciiPanel terminal) {
        for (int i=0;i<MESSAGE_WINDOW_SIZE;i++) {
            if(messages.isEmpty())
                return;
            Message message = messages.poll();
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
        int attackRoll = Dice.roll(20) + attackBonus;
        int damage = 0;
        
        String message = null;
        Color color = null;
        
        if (attackRoll == 20) {
            damage = damageBonus+damageDie;
            message = "You crit the " + e.getName() + ".";
            color = AsciiPanel.brightRed;
        } else if (attackRoll == 1) {
            damage = 0;
            message = "You wildly miss the " + e.getName() + ".";
            color = AsciiPanel.white;
        } else if (attackRoll > e.getAC()) {
            damage += damageBonus + Dice.roll(damageDie);
            message = "You hit the " + e.getName() + ".";
            color = Color.YELLOW;
        } else {
            damage = 0;
            message = "You miss the " + e.getName() + ".";
            color = AsciiPanel.white;
        }
        
        e.damage(damage);
        addMessage(new Message(message,color));
        if (e.getCurrentHp()<=0) {
            addMessage(new Message("You kill the " + e.getName(),AsciiPanel.brightGreen));
            regenFlask(1);
        }
    }
    
    @Override
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
        else if (k == KeyEvent.VK_Q) {
            quaff();
        } else
            updated = false;
        
        if (!updated)
            return this;
        
        time++;
        
        world.update();
        
        if (currentHP<=0)
            return new DieScreen();
        
        if (world.getEnemies().size() <= 0)
            return new WinScreen(time,fov.getExploredTiles(),MAX_ENEMIES-world.getNumEnemies());
        
        if (currentStepHP>=totalStepHP) {
            heal(1);
            currentStepHP = 0;
        } else {
            currentStepHP++;
        }
        
        if (currentStepFC>=totalStepFC) {
            regenFlask(1);
            currentStepFC = 0;
        } else {
            currentStepFC++;
        }
        return this;
    }

    private void quaff() {
        if (currentFC < quaffFC) {
            addMessage(new Message("Your flask is empty!",AsciiPanel.brightRed));
            return;
        }
        currentFC -= quaffFC;
        heal(20);
        addMessage(new Message("You take a quaff from your flask.",AsciiPanel.brightGreen));
    }
}
