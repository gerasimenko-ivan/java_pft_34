package ru.stqa.pft.sandbox;

/**
 * Created by gis on 15.11.2016.
 */
public class Equality {

    public static void main(String[] args) {
        String s1 = "firefox 2.0";
        String s2 = "firefox " + Math.sqrt(4.0);

        System.out.println(s1 == s2);       // link comparison
        System.out.println(s1.equals(s2));  // logic comparison
    }
}
