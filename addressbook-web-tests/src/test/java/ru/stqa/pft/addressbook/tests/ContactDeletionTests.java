package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

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

        int contactCountBefore = app.getContactHelper().getContactCount();
        app.getContactHelper().selectContact(contactCountBefore - 1);
        app.getContactHelper().deleteSelectedContract();
        app.getAlertHelper().accept();
        app.getNavigationHelper().gotoHome();

        int contactCountAfter = app.getContactHelper().getContactCount();
        Assert.assertEquals(contactCountAfter, contactCountBefore - 1);
    }
}
