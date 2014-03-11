package game.enemy;

import game.world.World;
import java.awt.Color;

public class Enemy {
    
    private int x;
    private int y;
    private int maxHp;
    private int currentHp;
    private char glyph;
    private String name;
    private Color color;
    private World world;
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public char getGlyph() { return glyph; }
    public String getName() { return name; }
    public Color getColor() { return color; }
    
    public Enemy(int x, int y, int hp, char glyph, String name, Color color, World world, int visionRadius) {
        this.x = x;
        this.y = y;
        this.maxHp = hp;
        this.currentHp = hp;
        this.glyph = glyph;
        this.name = name;
        this.color = color;
        this.world = world;
    }
}