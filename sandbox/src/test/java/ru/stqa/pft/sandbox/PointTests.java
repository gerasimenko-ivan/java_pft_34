package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gis on 30.10.2016.
 */
public class PointTests {

    @Test
    public void testDistance() {
        Point a = new Point(1, 1);
        Assert.assertEquals(a.distance(a), 0.0);
        Assert.assertEquals(Point.distance(a, a), 0.0);

        Point b = new Point(-2, -3);
        Assert.assertEquals(a.distance(b), 5.0);
        Assert.assertEquals(Point.distance(a, b), 5.0);

        Point c = new Point(1, -2);
        Assert.assertEquals(a.distance(c), 3.0);
        Assert.assertEquals(Point.distance(a, c), 3.0);

        Point d = new Point(-5, 2);
        Point e = new Point(-6, 1);
        double distanceEtoD = 1.4142135623730951;
        Assert.assertEquals(d.distance(e), distanceEtoD);
        Assert.assertEquals(Point.distance(d, e), distanceEtoD);
    }
}
