package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gis on 07.11.2016.
 */
public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.navigateTo().home();
        if (! app.contact().doesExist()) {
            ContactData contactData = ContactData.getWithRandomData();
            app.contact().create(contactData);
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts contactsBefore = app.contact().all();

        ContactData deletedContact = contactsBefore.getRandom();
        app.contact().selectById(deletedContact.getId());
        app.contact().deleteSelected();
        app.getAlertHelper().accept();
        app.navigateTo().home();

        // assertions
        assertThat(app.contact().getContactCount(), equalTo(contactsBefore.size() - 1));
        Contacts contactsAfter = app.contact().all();
        assertThat(contactsAfter, equalTo(contactsBefore.without(deletedContact)));
    }
}
