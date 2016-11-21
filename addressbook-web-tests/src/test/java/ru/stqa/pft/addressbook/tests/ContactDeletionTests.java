package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

/**
 * Created by gis on 07.11.2016.
 */
public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
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
        app.getContactHelper().selectContact(contactsBefore.size() - 1);
        app.getContactHelper().deleteSelectedContract();
        app.getAlertHelper().accept();
        app.getNavigationHelper().gotoHome();

        List<ContactData> contactsAfter = app.getContactHelper().getContactList();
        Assert.assertEquals(contactsAfter.size(), contactsBefore.size() - 1);
    }
}
