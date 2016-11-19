package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.HelperBase.FormAction;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

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

        app.getContactHelper().initContactCreation();

        ContactData contactData = new ContactData();
        contactData
                .setFirstname("Yan")
                .setMiddlename("E.")
                .setLastname("Doe")
                .setTitle("Dr.")
                .setAddress("221B Baker Street London")
                .setHomePhone("9(2131)324-33-33")
                .setEmail("test@test.ts")
                .setGroup(groupName);

        app.getContactHelper().fillContactForm(contactData, FormAction.CREATION);
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();
    }

}
