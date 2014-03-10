package game.util;

public class MathHelper {
    
    // This is beautiful.
    public static int median(int a, int b, int c) {
        return (a<=b)?((b<=c)?b:((a<c)?c:a)):((a<=c)?a:((b<c)?c:b));
    }
}