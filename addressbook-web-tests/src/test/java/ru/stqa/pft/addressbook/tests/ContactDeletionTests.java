package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

/**
 * Created by gis on 07.11.2016.
 */
public class ContactDeletionTests extends TestBase {

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
    public void testContactDeletion() {
        List<ContactData> contactsBefore = app.getContactHelper().getContactList();

        int index = rnd.getInt(0, contactsBefore.size() - 1);
        app.getContactHelper().selectContact(index);
        app.getContactHelper().deleteSelectedContract();
        app.getAlertHelper().accept();
        app.navigateTo().gotoHome();

        List<ContactData> contactsAfter = app.getContactHelper().getContactList();
        Assert.assertEquals(contactsAfter.size(), contactsBefore.size() - 1);

        contactsBefore.remove(index);
        Assert.assertEquals(contactsBefore, contactsAfter);
    }
}
