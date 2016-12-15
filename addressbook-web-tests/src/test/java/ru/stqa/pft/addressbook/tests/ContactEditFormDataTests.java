package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by gis on 11.12.2016.
 */
public class ContactEditFormDataTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            ContactData contactData = ContactData.getWithRandomData();
            app.contact().create(contactData);
        }
    }

    @Test
    public void testContactEditFormData() {
        app.navigateTo().home();
        ContactData contactFromDb = app.db().contacts().getRandom();
        ContactData contactDataFromEditForm = app.contact().getInfoFromEditFormById(contactFromDb.getId());

        // assertions
        assertThat(contactFromDb.withoutGroups(), equalTo(contactDataFromEditForm));
        verifyContactListInUI();
    }
}
