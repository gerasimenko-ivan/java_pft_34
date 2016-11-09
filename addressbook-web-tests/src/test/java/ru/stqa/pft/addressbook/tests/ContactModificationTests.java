package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

/**
 * Created by gis on 09.11.2016.
 */
public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHome();
        app.getContactHelper().initContactModification();

        ContactData contactData = new ContactData();
        contactData
                .setFirstname("Lee")
                .setLastname("Chan")
                .setTitle("Mr.")
                .setAddress("Flat D, 6/F, Golden Industrial Center, Block 4, 182-190 Tai Lin Pai Road, Kwai Chung")
                .setHomePhone("(852) 2877-8933")
                .setEmail("hongkong@ihg.com");

        app.getContactHelper().fillContactForm(contactData);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHomePage();
    }
}
