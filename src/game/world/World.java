package game.world;

import game.enemy.Enemy;
import game.enemy.EnemyOrcFighter;
import game.screens.PlayScreen;
import java.util.ArrayList;

public class World {
    
    private ArrayList<Area> areas;
    private int level;
    private PlayScreen screen;
    
    private ArrayList<Enemy> enemies;
    
    public int getWidth() { return areas.get(level).getWidth(); }
    public int getHeight() { return areas.get(level).getHeight(); }
    
    public Tile getTile(int x, int y) { return areas.get(level).getTile(x,y); }
    
    public ArrayList<Enemy> getEnemies() { return enemies; }
    
    public World(int width, int height, int level, PlayScreen screen) {
        this.screen = screen;
        byte type;
        
        int r = level+(int)(Math.random()*3);
        
        if (r<5)
            type = Area.TYPE_DUNGEON;
        else if (r<10)
            type = Area.TYPE_CAVE;
        else
            type = Area.TYPE_TREASURE;
        
        // For testing.
        type = Area.TYPE_CAVE;
        
        areas = new ArrayList<Area>();
        enemies = new ArrayList<Enemy>();
        areas.add(new Area(width,height,type));
    }
    
    public void update() {
        ArrayList<Enemy> remove = new ArrayList<Enemy>();
        for (Enemy e : enemies) {
            if (e.getCurrentHp() <= 0)
                remove.add(e);
            else
                e.update();
        }
        
        enemies.removeAll(remove);
    }

    public void addEnemy() {
        int x,y;
        do {
            x = (int)(Math.random()*areas.get(level).getWidth());
            y = (int)(Math.random()*areas.get(level).getWidth());
        } while (!getTile(x,y).isPassable);
        enemies.add(new EnemyOrcFighter(x,y,screen));
    }

    public Enemy getEnemy(int x, int y) {
        for (Enemy e : enemies)
            if (e.getX()==x && e.getY()==y)
                return e;
        return null;
    }
}