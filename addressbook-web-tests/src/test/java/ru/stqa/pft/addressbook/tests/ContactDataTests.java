package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by gis on 03.12.2016.
 */
public class ContactDataTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.navigateTo().home();
        if (! app.contact().doesExist()) {
            ContactData contactData = new ContactData();
            contactData
                    .withFirstname(rnd.getFirstnameEng())
                    .withMiddlename("E.")
                    .withLastname(rnd.getSurnameEng())
                    .withTitle("Dr.")
                    .withAddress(rnd.getAddressEng())
                    .withHomePhone(rnd.getPhone())
                    .withMobilePhone(rnd.getPhone())
                    .withWorkPhone(rnd.getPhone())
                    .withEmail(rnd.getEmail());
            app.contact().create(contactData);
        }
    }

    @Test
    public void testContactData() {
        app.navigateTo().home();
        ContactData contact = app.contact().all().getRandom();
        ContactData contactDataFromEditForm = app.contact().getInfoFromEditFormById(contact.getId());

        // assertions
        assertThat(contact, equalTo(contactDataFromEditForm));
        assertThat(contact.getAddress(), equalTo(contactDataFromEditForm.getAddress()));
        assertThat(contact.getHomePhone(), equalTo(cleaned(contactDataFromEditForm.getHomePhone())));
        assertThat(contact.getMobilePhone(), equalTo(cleaned(contactDataFromEditForm.getMobilePhone())));
        assertThat(contact.getWorkPhone(), equalTo(cleaned(contactDataFromEditForm.getWorkPhone())));
        assertThat(contact.getEmail(), equalTo(contactDataFromEditForm.getEmail()));
    }

    public String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
