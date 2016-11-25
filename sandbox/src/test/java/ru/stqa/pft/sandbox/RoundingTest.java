package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by gis on 25.11.2016.
 */
public class RoundingTest {

    @Test
    public void testRounding() {
        Rounding rnd = new Rounding();
        int a = rnd.getInt(2, 4, 0);
        Assert.assertEquals(a, 2);
        a = rnd.getInt(2, 4, 0.33333333333);
        Assert.assertEquals(a, 2);
        a = rnd.getInt(2, 4, 0.33333333334);
        Assert.assertEquals(a, 3);
        a = rnd.getInt(2, 4, 0.66666666666);
        Assert.assertEquals(a, 3);
        a = rnd.getInt(2, 4, 0.66666666667);
        Assert.assertEquals(a, 4);
        a = rnd.getInt(2, 4, 0.99999999999);
        Assert.assertEquals(a, 4);
    }
}
