package game.util;

import game.world.Tile;
import game.world.World;

public class FieldOfView {
    
    private World world;
    private boolean[][] visible;
    private Tile[][] tiles;
    
    public boolean isVisible(int x, int y) {
        return x>=0 && y>=0 && x<visible.length && y<visible[0].length && visible[x][y];
    }
    
    public Tile tile(int x, int y) {
        return tiles[x][y];
    }
 
    public FieldOfView(World world) {
        this.world = world;
        this.visible = new boolean[world.getWidth()][world.getHeight()];
        this.tiles = new Tile[world.getWidth()][world.getHeight()];
        
        blankFOV();
    }
    
    public void blankFOV() {
        for (int i=0;i<world.getWidth();i++) {
            for (int j=0;j<world.getHeight();j++) {
                tiles[i][j] = Tile.BLANK;
            }
        }
    }
    
    public void update(int x, int y, int r) {
        this.visible = new boolean[world.getWidth()][world.getHeight()];
        
        for (int i=-r;i<=r;i++) {
            for (int j=-r;j<=r;j++) {
                if (i*i+j*j>=r*r)
                    continue;
                
                if (x+i< 0 || x+i>=world.getWidth() || y+j<0 || y+j >= world.getHeight())
                    continue;
                
                for (Point p : new Line(x,y,x+i,y+j)) {
                    Tile tile = world.getTile(p.getX(),p.getY());
                    visible[p.getX()][p.getY()] = true;
                    tiles[p.getX()][p.getY()] = tile;
                    
                    if (!tile.isTransparent)
                        break;
                }
            }
        }
    }
}