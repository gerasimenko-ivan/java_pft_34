package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

/**
 * Created by gis on 07.11.2016.
 */
public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        app.getNavigationHelper().isOnGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test", null, null));
        }

        int groupCountBefore = app.getGroupHelper().getGroupCount();
        app.getGroupHelper().selectGroup(groupCountBefore - 1);
        app.getGroupHelper().initGroupModification();
        GroupData groupData = new GroupData("new-test1", "new-test2", "new-test3");
        app.getGroupHelper().fillGroupForm(groupData);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();

        int groupCountAfter = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(groupCountAfter, groupCountBefore);
    }
}
