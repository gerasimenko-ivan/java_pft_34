package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
    private String groupName = "test1";

    @BeforeClass
    public void ensurePreconditions() {
        app.navigateTo().groupPage();
        if (! app.group().doesExist(groupName)) {
            GroupData groupData = new GroupData().withName(groupName);
            app.group().create(groupData);
        }
    }
    
    @Test
    public void testContactCreation() {
        app.navigateTo().home();
        Contacts contactsBefore = app.contact().all();
        ContactData contact = new ContactData()
                .withFirstname(rnd.getFirstnameEng())
                .withMiddlename("E.")
                .withLastname(rnd.getSurnameEng())
                .withTitle("Dr.")
                .withAddress(rnd.getAddressEng())
                .withHomePhone("9(2131)324-33-33")
                .withEmail("test@test.ts")
                .withGroup(groupName);
        app.contact().create(contact);

        // assertions
        Contacts contactsAfter = app.contact().all();
        assertThat(contactsAfter.size(), equalTo(contactsBefore.size() + 1));

        contact.withId(contactsAfter.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        assertThat(contactsAfter, equalTo(contactsBefore.withAdded(contact)));
    }

}
