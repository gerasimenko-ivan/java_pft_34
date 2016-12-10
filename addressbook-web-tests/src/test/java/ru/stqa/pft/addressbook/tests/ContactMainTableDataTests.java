package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by gis on 03.12.2016.
 */
public class ContactMainTableDataTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.navigateTo().home();
        if (! app.contact().doesExist()) {
            ContactData contactData = ContactData.getWithRandomData();
            app.contact().create(contactData);
        }
    }

    @Test
    public void testContactMainTableData() {
        app.navigateTo().home();
        ContactData contact = app.contact().all().getRandom();
        ContactData contactDataFromEditForm = app.contact().getInfoFromEditFormById(contact.getId());

        // assertions
        assertThat(contact, equalTo(contactDataFromEditForm));
        assertThat(contact.getAddress(), equalTo(contactDataFromEditForm.getAddress() + ";"));
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactDataFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactDataFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactMainTableDataTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
