package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        app.getNavigationHelper().isOnGroupPage();

        int groupCountBefore = app.getGroupHelper().getGroupCount();

        GroupData groupData = new GroupData("test1", null, null);
        app.getGroupHelper().createGroup(groupData);

        int groupCountAfter = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(groupCountAfter, groupCountBefore + 1);
    }

}
