package game.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

    private int x;
    private int y;

    public int getX() { return x; }
    public int getY() { return y; }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj)
            return true;
        if (obj==null)
            return false;
        if (!(obj instanceof Point))
            return false;
        Point other = (Point) obj;
        if (x!=other.x)
            return false;
        if (y!=other.y)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67*hash+x;
        hash = 67*hash+y;
        return hash;
    }

    public List<Point> getNeighbors() {
        List<Point> points = new ArrayList<Point>();
        for (int ox = -1; ox<2; ox++) {
            for (int oy = -1; oy<2; oy++) {
                if (ox==0&&oy==0) {
                    continue;
                }
                points.add(new Point(x+ox,y+oy));
            }
        }
        Collections.shuffle(points);
        return points;
    }
}
