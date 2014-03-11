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
    private int visionRadius;
}