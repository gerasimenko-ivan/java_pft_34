package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gis on 15.12.2016.
 */
public class ContactFromGroupRemovalTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        // at least one contact attached to group
        if (contactsInGroups().size() == 0) {
            if (app.db().groups().size() == 0) {
                app.navigateTo().groupPage();
                app.group().create(new GroupData().withName("test_group"));
            }
            if (app.db().contacts().size() == 0) {
                app.navigateTo().home();
                app.contact().create(ContactData.getWithRandomData().inGroup(app.db().groups().getRandom()));
            }
        }
    }

    @Test
    public void testContactFromGroupRemoval() {
        Contacts contactsBefore = app.db().contacts();
        ContactData contact = contactsInGroups().getRandom();
        GroupData group = contact.getGroups().getRandom();

        app.navigateTo().home();
        app.contact().selectGroupToDisplay(group.getName());
        app.contact().selectById(contact.getId());
        app.contact().clickRemoveFromGroupButton(group.getName());
        app.contact().gotoHomePageForGroup(group.getName());

        Contacts contactsAfter = app.db().contacts();
        assertThat(contactsAfter,
                equalTo(contactsBefore
                            .without(contact)
                            .withAdded(contact.leaveGroup(group))));
    }

    public static Contacts contactsInGroups() {
        List<ContactData> contacts =
                app.db().contacts().stream()
                        .filter((c) -> c.getGroups().size() > 0).collect(Collectors.toList());
        return new Contacts(contacts);
    }
}
