package ru.stqa.pft.addressbook.model;

public class ContactData {
    private String firstname;
    private String middlename;
    private String lastname;
    private String title;
    private String address;
    private String homePhone;
    private String email;

    public ContactData() {}

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

    public ContactData setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getMiddlename() {
        return middlename;
    }

    public ContactData setMiddlename(String middlename) {
        this.middlename = middlename;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public ContactData setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ContactData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ContactData setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public ContactData setHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactData setEmail(String email) {
        this.email = email;
        return this;
    }
}
