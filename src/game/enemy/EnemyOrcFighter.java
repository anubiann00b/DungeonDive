package game.enemy;

import asciiPanel.AsciiPanel;
import game.screens.PlayScreen;
import game.util.Message;
import java.awt.Color;

public class EnemyOrcFighter extends Enemy {

    public EnemyOrcFighter(int x, int y, PlayScreen screen) {
        super(x,y,15,'o',"Orc Fighter",AsciiPanel.red,screen);
    }
    
    @Override
    public void update() {
        int px = screen.getPlayerX();
        int py = screen.getPlayerY();
        
        moveBy((int)(Math.random()*3)-1,(int)(Math.random()*3)-1);
    }
    
    private void moveBy(int dx, int dy) {
        if (x+dx==screen.getPlayerX() && y+dy==screen.getPlayerY()) {
            attack();
            return;
        }
        
        Enemy e = world.getEnemy(x+dx,y+dy);
        
        if (e==null && world.getTile(x+dx,y+dy).isPassable) {
            x += dx;
            y += dy;
        }
    }
    
    public void attack() {
        String message = getName() + " hits you.";
        screen.addMessage(new Message(message,Color.RED));
    }
}