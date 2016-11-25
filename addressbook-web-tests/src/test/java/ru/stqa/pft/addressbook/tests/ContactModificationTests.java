package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by gis on 09.11.2016.
 */
public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.navigateTo().gotoHome();
        if (! app.getContactHelper().isThereAContact()) {
            ContactData contactData = new ContactData();
            contactData
                    .setFirstname("Testname")
                    .setLastname("Testsurname");
            app.getContactHelper().createContact(contactData);
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> contactsBefore = app.getContactHelper().getContactList();

        int index = rnd.getInt(0, contactsBefore.size() - 1);
        ContactData contact = new ContactData();
        contact
                .setId(contactsBefore.get(index).getId())
                .setFirstname(rnd.getFirstnameEng())
                .setLastname(rnd.getSurnameEng())
                .setTitle("Ms.")
                .setAddress(rnd.getAddressEng())
                .setHomePhone("(852) 2877-8933")
                .setEmail("hongkong@ihg.com");

        app.getContactHelper().modifyContact(index, contact);

        List<ContactData> contactsAfter = app.getContactHelper().getContactList();
        Assert.assertEquals(contactsAfter.size(), contactsBefore.size());

        contactsBefore.remove(index);
        contactsBefore.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        contactsBefore.sort(byId);
        contactsAfter.sort(byId);
        Assert.assertEquals(contactsAfter, contactsBefore);
    }
}
