package game.enemy;

import game.screens.PlayScreen;
import game.world.World;
import java.awt.Color;

public class Enemy {
    
    protected int x;
    protected int y;
    protected int maxHp;
    protected int currentHp;
    protected char glyph;
    protected String name;
    protected Color color;
    protected World world;
    protected PlayScreen screen;
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public char getGlyph() { return glyph; }
    public String getName() { return name; }
    public Color getColor() { return color; }
    
    public Enemy(int x, int y, int hp, char glyph, String name, Color color, PlayScreen screen) {
        this.x = x;
        this.y = y;
        this.maxHp = hp;
        this.currentHp = hp;
        this.glyph = glyph;
        this.name = name;
        this.color = color;
        this.screen = screen;
        this.world = screen.getWorld();
    }
    
    public void update() {
        
    }
}