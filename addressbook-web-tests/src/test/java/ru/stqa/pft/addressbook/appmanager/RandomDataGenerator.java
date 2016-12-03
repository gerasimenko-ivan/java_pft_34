package ru.stqa.pft.addressbook.appmanager;

/**
 * Created by gis on 25.11.2016.
 */
public class RandomDataGenerator {
    public int getInt(int min, int max) {
        return min + (int)(Math.random()*(max - min + 1));
    }

    public String getFirstnameEng() {
        String[] firstnames = {"Alex", "Bob", "Elvise", "Glenn", "Henrich", "Jack", "Pit", "Sam", "Ted", "Yen"};
        return firstnames[getInt(0, firstnames.length - 1)];
    }

    public String getSurnameEng() {
        String[] surnames = {"Biber", "Clapton", "Davis", "Doe", "Lee", "Miller", "Smith", "Tramp", "Trammel"};
        return surnames[getInt(0, surnames.length - 1)];
    }

    public String getAddressEng() {
        String[] litera = {"", "", "", "", "", "A", "B", "C", "/4"};
        String[] streets = {"Baker Street", "Wall Street", "West Road"};
        String[] cities = {"Boston", "Lisboa", "Moscow", "New York", "Roma"};
        return getInt(1, 999) + litera[getInt(0, litera.length - 1)] +
                ", " + streets[getInt(0, streets.length - 1)] +
                ", " + cities[getInt(0, cities.length - 1)];
    }

    public String getEmail() {
        StringBuilder emailBuilder = new StringBuilder();
        emailBuilder.append(getFirstnameEng());
        if(Math.random() > 0.5) {
            emailBuilder.append(".");
        }
        emailBuilder.append(getSurnameEng());
        if(Math.random() > 0.2) {
            emailBuilder.append(getInt(0, 99999999));
        }

        emailBuilder.append("@mail.com");
        return emailBuilder.toString();
    }

    public String getPhone() {
        StringBuilder phoneBuilder = new StringBuilder();
        if (Math.random() > 0.5) {
            phoneBuilder.append("+");
        }
        phoneBuilder.append(getInt(0, 9));
        phoneBuilder.append("(");
        phoneBuilder.append(getInt(100, 999));
        phoneBuilder.append(")");
        phoneBuilder.append(getInt(100, 999));
        phoneBuilder.append("-");
        phoneBuilder.append(getInt(10, 99));
        phoneBuilder.append("-");
        phoneBuilder.append(getInt(10, 99));

        return phoneBuilder.toString();
    }
}
