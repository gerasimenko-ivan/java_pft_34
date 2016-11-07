package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
    
    @Test
    public void testContactCreation() {
        app.getContactHelper().initContactCreation();
        app.getNavigationHelper().isOnEditNewContactPage();
        ContactData contactData = new ContactData("John", "W.", "Doe", "Dr.", "221B Baker Street London", "9(2131)324-33-33", "test@test.ts");
        app.getContactHelper().fillContactForm(contactData);
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();
    }

}
