package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by gis on 07.11.2016.
 */
public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHome();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContract();
        app.getAlertHelper().accept();
        app.getNavigationHelper().gotoHome();
    }
}
