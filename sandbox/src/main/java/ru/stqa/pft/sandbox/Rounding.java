package ru.stqa.pft.sandbox;

/**
 * Created by gis on 25.11.2016.
 */
public class Rounding {
    public static void main(String[] args) {
        int a = (int)0.5;
        System.out.println("int a = (int)0.5; // = " + a);
        a = (int)0.9999999;
        System.out.println("a = (int)0.9999999; // = " + a);
        a = (int)5.9999999;
        System.out.println("a = (int)5.9999999; // = " + a);
    }

    public int getInt(int min, int max, double random) {
        return min + (int)(random*(max - min + 1));
    }
}
