package game.enemy;

import asciiPanel.AsciiPanel;
import game.screens.PlayScreen;
import game.util.Dice;
import game.util.Message;
import java.awt.Color;

public class EnemyOrcFighter extends Enemy {

    public EnemyOrcFighter(int x, int y, PlayScreen screen) {
        super(x,y,15,'o',"Orc Fighter",AsciiPanel.red,screen);
        diceSides = 6;
        bonus = 3;
    }
    
    @Override
    public void update() {
        
        fov.update(x,y,9);
        
        int px = screen.getPlayerX();
        int py = screen.getPlayerY();
        
        if (!fov.isVisible(px,py))
            return;
        
        int cdx = 0;
        int cdy = 0;
        
        double currentWorth = 10000;
        
        for (int ox=-1;ox<=1;ox++) {
            for (int oy=-1;oy<=1;oy++) {
                if (ox==0 && oy==0)
                    continue;
                if (!world.getTile(x+ox,y+oy).isPassable || world.getEnemy(x+ox,y+oy)!=null)
                    continue;
                double worth = (x+ox-px)*(x-px+ox) + (y+oy-py)*(y+oy-py);
                if (worth < currentWorth) {
                    currentWorth = worth;
                    cdx = ox;
                    cdy = oy;
                }
            }
        }
        moveBy(cdx,cdy);
    }
    
    private void moveBy(int dx, int dy) {
        if (x+dx==screen.getPlayerX() && y+dy==screen.getPlayerY()) {
            attack();
            return;
        }
        
        x += dx;
        y += dy;
    }
    
    public void attack() {
        int d20 = Dice.roll(20);
        int damage = 0;
        String message = null;
        
        if (d20 == 20) {
            damage = bonus+diceSides;
            message = getName() + " crits you.";
        } else if (d20 == 1) {
            damage = 0;
            message = getName() + " wildly misses you.";
        } else if (d20 > screen.getPlayerAC()) {
            message = getName() + " hits you.";
            damage += bonus + Dice.roll(diceSides);
        } else {
            message = getName() + " misses you.";
        }
        
        screen.addMessage(new Message(message,Color.RED));
    }
}