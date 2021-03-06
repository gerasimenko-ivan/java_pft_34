package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.model.GroupData.ALL;

/**
 * Created by gis on 09.11.2016.
 */
public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.navigateTo().home();
            ContactData contactData = ContactData.getWithRandomData();
            app.contact().create(contactData);
        }
    }

    @Test
    public void testContactModification() {
        Contacts contactsBefore = app.db().contacts();

        ContactData contactOld = contactsBefore.getRandom();
        ContactData contactNew = ContactData.getWithRandomData()
                .withId(contactOld.getId());

        app.navigateTo().home();
        app.contact().selectGroupToDisplay(ALL);
        app.contact().modifyById(contactOld.getId(), contactNew);

        // assertions
        assertThat(app.contact().getContactCount(), equalTo(contactsBefore.size()));
        Contacts contactsAfter = app.db().contacts();
        for (GroupData group : contactOld.getGroups()) {
            contactNew.inGroup(group);
        }
        assertThat(contactsAfter, equalTo(contactsBefore.withModified(contactOld, contactNew)));
        verifyContactListInUI();
    }

}
