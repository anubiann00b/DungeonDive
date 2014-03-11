package game.world;

public class World {
    
    private Area area;
    
    public int getWidth() { return area.getWidth(); }
    public int getHeight() { return area.getHeight(); }
    
    public Tile getTile(int x, int y) { return area.getTile(x,y); }
    
    public World(int width, int height, int level) {
        byte type;
        
        int r = level+(int)(Math.random()*3);
        
        if (r < 5)
            type = Area.TYPE_DUNGEON;
        else if (r < 10)
            type = Area.TYPE_CAVE;
        else
            type = Area.TYPE_TREASURE;
        
        // For testing.
        type = Area.TYPE_CAVE;
        
        area = new Area(width,height,type);
    }
}