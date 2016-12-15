package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
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
import static ru.stqa.pft.addressbook.model.GroupData.ALL;

/**
 * Created by gis on 15.12.2016.
 */
public class ContactToGroupAdditionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        // at least one group
        if (app.db().groups().size() == 0) {
            app.navigateTo().groupPage();
            app.group().create(new GroupData().withName("test_group"));
        }

        // at least one contact which is not added to all groups
        if (contactsNotAddedToAllGroups().size() == 0) {
            app.navigateTo().home();
            app.contact().create(ContactData.getWithRandomData());
        }
    }

    @Test
    public void testContactToGroupAddition() {
        ContactData contact = contactsNotAddedToAllGroups().getRandom();
        Groups groups = app.db().groups();
        GroupData group =
                new Groups(
                        groups.stream().filter(
                                (g) -> !contact.getGroups().contains(g)).collect(Collectors.toSet())
                ).getRandom();

        Contacts contactsBefore = app.db().contacts();
        app.navigateTo().home();
        app.contact().selectGroupToDisplay(ALL);
        app.contact().selectById(contact.getId());
        app.contact().selectGroupToAddTo(group.getName());
        app.contact().submitAddToGroup();
        app.contact().gotoHomePageForGroup(group.getName());

        Contacts contactsAfter = app.db().contacts();
        assertThat(contactsAfter, equalTo(contactsBefore.without(contact).withAdded(contact.inGroup(group))));
    }

    public static Contacts contactsNotAddedToAllGroups() {
        final int groupCount = app.db().groups().size();
        List<ContactData> contacts = app.db().contacts().stream().filter((c) -> c.getGroups().size() < groupCount).collect(Collectors.toList());
        return new Contacts(contacts);
    }
}
