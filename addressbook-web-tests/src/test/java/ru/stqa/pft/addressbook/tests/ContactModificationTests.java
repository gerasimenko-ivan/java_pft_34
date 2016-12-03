package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gis on 09.11.2016.
 */
public class ContactModificationTests extends TestBase {

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
    public void testContactModification() {
        Contacts contactsBefore = app.contact().all();

        ContactData contactOld = contactsBefore.getRandom();
        ContactData contactNew = new ContactData()
                .withId(contactOld.getId())
                .withFirstname(rnd.getFirstnameEng())
                .withLastname(rnd.getSurnameEng())
                .withTitle("Ms.")
                .withAddress(rnd.getAddressEng())
                .withHomePhone(rnd.getPhone())
                .withEmail(rnd.getEmail());

        app.contact().modifyById(contactOld.getId(), contactNew);

        // assertions
        assertThat(app.contact().getContactCount(), equalTo(contactsBefore.size()));
        Contacts contactsAfter = app.contact().all();
        assertThat(contactsAfter, equalTo(contactsBefore.withModified(contactOld, contactNew)));
    }
}
