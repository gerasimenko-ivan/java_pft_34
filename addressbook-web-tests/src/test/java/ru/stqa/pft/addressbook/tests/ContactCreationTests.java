package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.HelperBase.FormAction;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {
    
    @Test
    public void testContactCreation() {
        String groupName = "test1";

        app.getNavigationHelper().gotoGroupPage();
        app.getNavigationHelper().isOnGroupPage();
        if (! app.getGroupHelper().isThereAGroup(groupName)) {
            GroupData groupData = new GroupData(groupName, null, null);
            app.getGroupHelper().createGroup(groupData);
        }

        app.getNavigationHelper().gotoHome();
        List<ContactData> contactsBefore = app.getContactHelper().getContactList();

        app.getContactHelper().initContactCreation();

        ContactData contact = new ContactData();
        contact
                .setFirstname("Yan")
                .setMiddlename("E.")
                .setLastname("Doe")
                .setTitle("Dr.")
                .setAddress("221B Baker Street London")
                .setHomePhone("9(2131)324-33-33")
                .setEmail("test@test.ts")
                .setGroup(groupName);

        app.getContactHelper().fillContactForm(contact, FormAction.CREATION);
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();

        List<ContactData> contactsAfter = app.getContactHelper().getContactList();
        Assert.assertEquals(contactsAfter.size(), contactsBefore.size() + 1);

        contact.setId(contactsAfter.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        contactsBefore.add(contact);
        Assert.assertEquals(new HashSet<Object>(contactsAfter), new HashSet<Object>(contactsBefore));
    }

}
