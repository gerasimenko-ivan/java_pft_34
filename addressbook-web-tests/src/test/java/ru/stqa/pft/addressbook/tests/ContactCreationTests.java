package ru.stqa.pft.addressbook.tests;

import javafx.scene.effect.SepiaTone;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
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
        Set<ContactData> contactsBefore = app.contact().hashSet();
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

        Set<ContactData> contactsAfter = app.contact().hashSet();
        assertThat(contactsAfter.size(), equalTo(contactsBefore.size() + 1));

        contact.withId(contactsAfter.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        contactsBefore.add(contact);
        assertThat(contactsAfter, equalTo(contactsBefore));
    }

}
