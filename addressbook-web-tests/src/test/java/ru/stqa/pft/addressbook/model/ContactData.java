package ru.stqa.pft.addressbook.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;
import ru.stqa.pft.addressbook.appmanager.RandomDataGenerator;

import javax.persistence.*;
import java.io.File;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class ContactData {
    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "middlename")
    private String middlename;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "title")
    private String title;
    @Column(name = "address")
    @Type(type = "text")
    private String address;
    @Column(name = "home")
    @Type(type = "text")
    private String homePhone;
    @Column(name = "mobile")
    @Type(type = "text")
    private String mobilePhone;
    @Column(name = "work")
    @Type(type = "text")
    private String workPhone;
    @XStreamOmitField
    @Transient
    private String allPhones;
    @Column(name = "email")
    @Type(type = "text")
    private String email;
    @Column(name = "email2")
    @Type(type = "text")
    private String email2;
    @Column(name = "email3")
    @Type(type = "text")
    private String email3;
    @XStreamOmitField
    @Transient
    private String allEmails;
    @Transient
    private String group;
    @Column(name = "photo")
    @Type(type = "text")
    private String photo;

    public ContactData() {
        this.id = 0;
        this.firstname = "";
        this.middlename = "";
        this.lastname = "";
        this.title = "";
        this.address = "";
        this.homePhone = "";
        this.mobilePhone = "";
        this.workPhone = "";
        this.email = "";
        this.email2 = "";
        this.email3 = "";
        this.group = null;
    }

    public static ContactData getWithRandomData() {
        RandomDataGenerator rnd = new RandomDataGenerator();
        ContactData contact = new ContactData();
        contact
                .withFirstname(rnd.getFirstnameEng())
                .withMiddlename("E.")
                .withLastname(rnd.getSurnameEng())
                .withTitle("Dr.")
                .withAddress(rnd.getAddressEng())
                .withHomePhone(Math.random() > 0.3 ? rnd.getPhone() : "")
                .withMobilePhone(Math.random() > 0.5 ? rnd.getPhone() : "")
                .withWorkPhone(Math.random() > 0.5 ? rnd.getPhone() : "")
                .withEmail(Math.random() > 0.3 ? rnd.getEmail() : "")
                .withEmail2(Math.random() > 0.5 ? rnd.getEmail() : "")
                .withEmail3(Math.random() > 0.5 ? rnd.getEmail() : "");
        return contact;
    }

    public int getId() { return id; }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public ContactData withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getMiddlename() {
        return middlename;
    }

    public ContactData withMiddlename(String middlename) {
        this.middlename = middlename;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public ContactData withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ContactData withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public ContactData withHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public ContactData withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public ContactData withWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmail2() {
        return email2;
    }

    public ContactData withEmail2(String email) {
        this.email2 = email;
        return this;
    }

    public String getEmail3() {
        return email3;
    }

    public ContactData withEmail3(String email) {
        this.email3 = email;
        return this;
    }

    public String getAllEmails() {
        return allEmails;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ContactData withGroup(String group) {
        this.group = group;
        return this;
    }

    public File getPhoto() {
        return new File(photo);
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getAbsolutePath();
        return this;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    public boolean hasPhone() {
        return (!getHomePhone().equals("")) || (!getMobilePhone().equals("")) || (!getWorkPhone().equals(""));
    }

    public boolean hasEmail() {
        return (!getEmail().equals("")) || (!getEmail2().equals("")) || (!getEmail3().equals(""));
    }
}
