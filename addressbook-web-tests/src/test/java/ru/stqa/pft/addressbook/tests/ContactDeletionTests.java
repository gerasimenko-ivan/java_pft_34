package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;

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
            ContactData contactData = new ContactData();
            contactData
                    .withFirstname("Testname")
                    .withLastname("Testsurname");
            app.contact().create(contactData);
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts contactsBefore = app.contact().all();

        int index = rnd.getInt(0, contactsBefore.size() - 1);
        ContactData deletedContact = new ArrayList<ContactData>(contactsBefore).get(index);
        app.contact().select(index);
        app.contact().deleteSelected();
        app.getAlertHelper().accept();
        app.navigateTo().home();

        // assertions

        Contacts contactsAfter = app.contact().all();
        assertThat(contactsAfter.size(), equalTo(contactsBefore.size() - 1));
        assertThat(contactsAfter, equalTo(contactsBefore.without(deletedContact)));
    }
}
