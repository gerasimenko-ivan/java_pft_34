package ru.stqa.pft.addressbook.appmanager;

/**
 * Created by gis on 25.11.2016.
 */
public class RandomDataGenerator {
    public int getInt(int min, int max) {
        return min + (int)(Math.random()*(max - min + 1));
    }

    public String getFirstnameEng() {
        String[] firstnames = {"Bob", "Pit", "Sam", "Ted", "Yen"};
        return firstnames[getInt(0, firstnames.length - 1)];
    }

    public String getSurnameEng() {
        String[] surnames = {"Davis", "Doe", "Lee", "Smith", "Tramp"};
        return surnames[getInt(0, surnames.length - 1)];
    }
}
