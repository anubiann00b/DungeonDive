package game.world;

import asciiPanel.AsciiPanel;
import java.awt.Color;

public enum Tile {
    
    STONE_WALL('#',AsciiPanel.yellow,false,false),
    STONE_FLOOR('.',AsciiPanel.yellow,true,true);
    
    private char glyph;
    private Color color;
    private boolean passable;
    private boolean transparent;
    
    public char getGlyph() { return glyph; }
    public boolean isPassable() { return passable; }
    public boolean isTransparent() { return transparent; }
    
    Tile(char glyph, Color color, boolean passable, boolean transparent) {
        this.glyph = glyph;
        this.color = color;
        this.passable = passable;
        this.transparent = transparent;
    }
}