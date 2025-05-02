import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        Point2D p;
        RectHV rect;
        Node lb, rt;

        Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public KdTree() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, true, 0, 0, 1, 1);
    }

    private Node insert(Node n, Point2D p, boolean vertical, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        if (n.p.equals(p)) return n;

        if ((vertical && p.x() < n.p.x()) || (!vertical && p.y() < n.p.y())) {
            if (vertical) n.lb = insert(n.lb, p, !vertical, xmin, ymin, n.p.x(), ymax);
            else n.lb = insert(n.lb, p, !vertical, xmin, ymin, xmax, n.p.y());
        } else {
            if (vertical) n.rt = insert(n.rt, p, !vertical, n.p.x(), ymin, xmax, ymax);
            else n.rt = insert(n.rt, p, !vertical, xmin, n.p.y(), xmax, ymax);
        }
        return n;
    }


    private boolean contains(Node n, Point2D p, boolean vertical) {
        if (n == null) return false;
        if (n.p.equals(p)) return true;
        if ((vertical && p.x() < n.p.x()) || (!vertical && p.y() < n.p.y()))
            return contains(n.lb, p, !vertical);
        else return contains(n.rt, p, !vertical);
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node n, boolean vertical) {
        if (n == null) return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        n.p.draw();

        StdDraw.setPenRadius();
        StdDraw.setPenColor(vertical ? StdDraw.RED : StdDraw.BLUE);
        if (vertical)
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        else
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());

        draw(n.lb, !vertical);
        draw(n.rt, !vertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Stack<Point2D> result = new Stack<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node n, RectHV query, Stack<Point2D> result) {
        if (n == null || !query.intersects(n.rect)) return;
        if (query.contains(n.p)) result.push(n.p);
        range(n.lb, query, result);
        range(n.rt, query, result);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return nearest(root, p, root.p, true);
    }

    private Point2D nearest(Node n, Point2D target, Point2D best, boolean vertical) {
        if (n == null || n.rect.distanceSquaredTo(target) >= best.distanceSquaredTo(target)) return best;

        if (n.p.distanceSquaredTo(target) < best.distanceSquaredTo(target)) best = n.p;

        Node first = (vertical && target.x() < n.p.x()) || (!vertical && target.y() < n.p.y()) ? n.lb : n.rt;
        Node second = first == n.lb ? n.rt : n.lb;

        best = nearest(first, target, best, !vertical);
        best = nearest(second, target, best, !vertical);

        return best;
    }

    public static void main(String[] args) {

    }
}