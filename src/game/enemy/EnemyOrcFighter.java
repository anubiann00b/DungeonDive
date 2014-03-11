package game.enemy;

import asciiPanel.AsciiPanel;
import game.screens.PlayScreen;
import game.util.Dice;
import game.util.Message;
import java.awt.Color;

public class EnemyOrcFighter extends Enemy {

    public EnemyOrcFighter(int x, int y, PlayScreen screen) {
        super(x,y,15,'o',"Orc Fighter",AsciiPanel.red,screen);
        damageDie = 6;
        damageBonus = 3;
        attackBonus = 2;
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
        int attackRoll = Dice.roll(20) + attackBonus;
        int damage = 0;
        String message = null;
        
        if (attackRoll == 20) {
            damage = damageBonus+damageDie;
            message = getName() + " crits you.";
        } else if (attackRoll == 1) {
            damage = 0;
            message = getName() + " wildly misses you.";
        } else if (attackRoll > screen.getPlayerAC()) {
            message = getName() + " hits you.";
            damage += damageBonus + Dice.roll(damageDie);
        } else {
            message = getName() + " misses you.";
        }
        
        screen.damage(damage);
        screen.addMessage(new Message(message,Color.RED));
    }
}