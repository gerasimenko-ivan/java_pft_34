package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
        Set<ContactData> contactsBefore = app.contact().hashSet();

        int index = rnd.getInt(0, contactsBefore.size() - 1);
        ContactData contactOld = app.contact().getByIndex(index);
        ContactData contactNew = new ContactData()
                .withId(contactOld.getId())
                .withFirstname(rnd.getFirstnameEng())
                .withLastname(rnd.getSurnameEng())
                .withTitle("Ms.")
                .withAddress(rnd.getAddressEng())
                .withHomePhone("(852) 2877-8933")
                .withEmail("hongkong@ihg.com");

        app.contact().modify(index, contactNew);

        // assertions

        Set<ContactData> contactsAfter = app.contact().hashSet();
        assertThat(contactsAfter.size(), equalTo(contactsBefore.size()));

        contactsBefore.remove(contactOld);
        contactsBefore.add(contactNew);
        assertThat(contactsAfter, equalTo(contactsBefore));
    }
}
