package game.enemy;

import asciiPanel.AsciiPanel;
import game.screens.PlayScreen;

public class EnemyOrcFighter extends Enemy {

    public EnemyOrcFighter(int x, int y, PlayScreen screen) {
        super(x,y,15,'o',"Orc Fighter",AsciiPanel.red,screen);
    }
    
    @Override
    public void update() {
        moveBy(1,0);
    }
    
    private void moveBy(int dx, int dy) {
        if (world.getTile(x+dx,y+dy).isPassable) {
            x += dx;
            y += dy;
        }
    }
}