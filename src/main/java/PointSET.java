import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;
import java.util.ArrayList;

public class PointSET {
    private final TreeSet<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument cannot be null");
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument cannot be null");
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Argument cannot be null");
        ArrayList<Point2D> inside = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) inside.add(p);
        }
        return inside;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Argument cannot be null");
        if (isEmpty()) return null;

        Point2D nearest = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Point2D point : set) {
            double dist = point.distanceSquaredTo(p);
            if (dist < minDist) {
                minDist = dist;
                nearest = point;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}