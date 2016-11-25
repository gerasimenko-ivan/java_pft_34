package ru.stqa.pft.addressbook.appmanager;

/**
 * Created by gis on 25.11.2016.
 */
public class RandomDataGenerator {
    public int getInt(int min, int max) {
        return min + (int)(Math.random()*(max - min + 1));
    }
}
