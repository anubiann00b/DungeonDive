package game.util;

import java.util.Random;

public class Dice {
    
    private static Random r;
    
    static {
        r = new Random();
    }
    
    public static int roll(int sides) {
        return r.nextInt(sides)+1;
    }
}