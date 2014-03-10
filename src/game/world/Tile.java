package game.world;

import asciiPanel.AsciiPanel;
import java.awt.Color;

public enum Tile {
    
    STONE_WALL('#',AsciiPanel.yellow,false,false),
    STONE_FLOOR('.',AsciiPanel.yellow,true,true);
    
    public char glyph;
    public Color color;
    public boolean isPassable;
    public boolean isTransparent;
    
    Tile(char glyph, Color color, boolean isPassable, boolean isTransparent) {
        this.glyph = glyph;
        this.color = color;
        this.isPassable = isPassable;
        this.isTransparent = isTransparent;
    }
}