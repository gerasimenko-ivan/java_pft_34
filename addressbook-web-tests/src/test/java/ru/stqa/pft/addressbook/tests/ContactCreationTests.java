package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.HelperBase.FormAction;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
    
    @Test
    public void testContactCreation() {
        app.getContactHelper().initContactCreation();
        app.getNavigationHelper().isOnEditNewContactPage();

        ContactData contactData = new ContactData();
        contactData
                .setFirstname("Yan")
                .setMiddlename("E.")
                .setLastname("Doe")
                .setTitle("Dr.")
                .setAddress("221B Baker Street London")
                .setHomePhone("9(2131)324-33-33")
                .setEmail("test@test.ts")
                .setGroup("test1");

        app.getContactHelper().fillContactForm(contactData, FormAction.CREATION);
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();
    }

}
