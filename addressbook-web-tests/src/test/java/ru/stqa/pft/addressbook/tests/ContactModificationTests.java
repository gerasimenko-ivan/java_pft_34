package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.HelperBase.FormAction;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

/**
 * Created by gis on 09.11.2016.
 */
public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHome();
        if (! app.getContactHelper().isThereAContact()) {
            ContactData contactData = new ContactData();
            contactData
                    .setFirstname("Testname")
                    .setLastname("Testsurname");
            app.getContactHelper().createContact(contactData);
            app.getNavigationHelper().gotoHomePage();
        }

        List<ContactData> contactsBefore = app.getContactHelper().getContactList();

        ContactData contact = new ContactData();
        contact
                .setId(contactsBefore.get(contactsBefore.size() - 1).getId())
                .setFirstname("Foo")
                .setLastname("Khun")
                .setTitle("Ms.")
                .setAddress("Flat D, 6/F, Golden Industrial Center, Block 4, 182-190 Tai Lin Pai Road, Kwai Chung")
                .setHomePhone("(852) 2877-8933")
                .setEmail("hongkong@ihg.com");

        app.getContactHelper().modifyContact(contactsBefore.size() - 1, contact);
        app.getNavigationHelper().gotoHomePage();

        List<ContactData> contactsAfter = app.getContactHelper().getContactList();
        Assert.assertEquals(contactsAfter.size(), contactsBefore.size());

        contactsBefore.remove(contactsBefore.size() - 1);
        contactsBefore.add(contact);
        Assert.assertEquals(new HashSet<Object>(contactsAfter), new HashSet<Object>(contactsBefore));
    }
}
