package ru.stqa.pft.addressbook.tests;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static ru.stqa.pft.addressbook.model.GroupData.ALL;

/**
 * Created by gis on 03.12.2016.
 */
public class ContactMainTableDataTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            ContactData contactData = ContactData.getWithRandomData();
            app.contact().create(contactData);
        }
    }

    @Test
    public void testContactMainTableData() {
        app.navigateTo().home();
        app.contact().selectGroupToDisplay(ALL);
        ContactData contactFromMainTable = app.contact().all().getRandom();
        ContactData contactFromDb = app.db().contacts().stream().filter(c -> c.getId() == contactFromMainTable.getId()).findFirst().get();

        // assertions
        assertThat(contactFromMainTable, byIdFirstLastNameAndAddressEqualTo(contactFromDb));
        //assertThat(contactFromMainTable.getAddress(), equalTo(contactFromDb.getAddress()));
        assertThat(contactFromMainTable.getAllPhones(), equalTo(mergePhones(contactFromDb)));
        assertThat(contactFromMainTable.getAllEmails(), equalTo(mergeEmails(contactFromDb)));
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

    private Matcher<ContactData> byIdFirstLastNameAndAddressEqualTo(final ContactData contactExpected) {
        return new BaseMatcher<ContactData>() {
            @Override
            public boolean matches(final Object objectActual) {
                final ContactData contactActual = (ContactData) objectActual;

                if (contactActual.getId() != contactExpected.getId()) return false;
                if (contactActual.getFirstname() != null ? !contactActual.getFirstname().equals(contactExpected.getFirstname()) : contactExpected.getFirstname() != null) return false;
                if (contactActual.getLastname() != null ? !contactActual.getLastname().equals(contactExpected.getLastname()) : contactExpected.getLastname() != null) return false;
                return contactActual.getAddress() != null ? contactActual.getAddress().equals(contactExpected.getAddress()) : contactExpected.getAddress() == null;
            }
            @Override
            public void describeTo(final Description description) {
                description.appendText("getNumber should return ").appendValue(contactExpected);
            }
            @Override
            public void describeMismatch(final Object objectActual, final
            Description description) {
                description.appendText("was ").appendValue(((ContactData) objectActual));
            }
        };
    }
}
