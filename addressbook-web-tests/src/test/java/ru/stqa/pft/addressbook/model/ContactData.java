package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final String title;
    private final String address;
    private final String homePhone;
    private final String email;

    public ContactData(String firstname, String middlename, String lastname, String title, String address, String homePhone, String email) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.title = title;
        this.address = address;
        this.homePhone = homePhone;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getEmail() {
        return email;
    }
}
