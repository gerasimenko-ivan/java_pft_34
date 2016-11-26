package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

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
        List<ContactData> contactsBefore = app.contact().list();

        ContactData contact = new ContactData();
        contact
                .setId(Integer.MAX_VALUE)
                .setFirstname(rnd.getFirstnameEng())
                .setMiddlename("E.")
                .setLastname(rnd.getSurnameEng())
                .setTitle("Dr.")
                .setAddress(rnd.getAddressEng())
                .setHomePhone("9(2131)324-33-33")
                .setEmail("test@test.ts")
                .setGroup(groupName);
        app.contact().create(contact);

        // assertions

        List<ContactData> contactsAfter = app.contact().list();
        Assert.assertEquals(contactsAfter.size(), contactsBefore.size() + 1);

        contactsBefore.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        contactsBefore.sort(byId);
        contactsAfter.sort(byId);
        Assert.assertEquals(contactsAfter, contactsBefore);
    }

}
