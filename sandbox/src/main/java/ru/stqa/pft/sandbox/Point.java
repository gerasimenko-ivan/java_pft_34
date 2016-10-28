package ru.stqa.pft.sandbox;

/**
 * Created by gis on 28.10.2016.
 */
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "{" + this.x + "; " + this.y + "}";
    }

    public static double distance(Point point1, Point point2) {
        double xDelta = point1.x - point2.x;
        double yDelta = point1.y - point2.y;
        return Math.sqrt(xDelta*xDelta + yDelta*yDelta);
    }

    public  double distance(Point point) {
        return distance(this, point);
    }
}
