package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

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
        File photo = new File("src/test/resources/vinni.jpeg");
        ContactData contact = ContactData.getWithRandomData()
                .withGroup(groupName)
                .withPhoto(photo);
        app.contact().create(contact);

        // assertions
        assertThat(app.contact().getContactCount(), equalTo(contactsBefore.size() + 1));
        Contacts contactsAfter = app.contact().all();
        contact.withId(contactsAfter.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        assertThat(contactsAfter, equalTo(contactsBefore.withAdded(contact)));
    }

}
