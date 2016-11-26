package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;
import java.util.Set;

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
        Set<ContactData> contactsBefore = app.contact().hashSet();

        int index = rnd.getInt(0, contactsBefore.size() - 1);
        ContactData deletedContact = app.contact().getByIndex(index);
        app.contact().select(index);
        app.contact().deleteSelected();
        app.getAlertHelper().accept();
        app.navigateTo().home();

        // assertions

        Set<ContactData> contactsAfter = app.contact().hashSet();
        assertThat(contactsAfter.size(), equalTo(contactsBefore.size() - 1));

        contactsBefore.remove(deletedContact);
        assertThat(contactsAfter, equalTo(contactsBefore));
    }
}
