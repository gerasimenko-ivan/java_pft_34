package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

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

        List<GroupData> groupsBefore = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(groupsBefore.size() - 1);
        app.getGroupHelper().initGroupModification();
        GroupData group = new GroupData(groupsBefore.get(groupsBefore.size() - 1).getId(), "new-test1", "new-test2", "new-test3");
        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();

        List<GroupData> groupsAfter = app.getGroupHelper().getGroupList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size());

        groupsBefore.remove(groupsBefore.size() - 1);
        groupsBefore.add(group);
        Assert.assertEquals(new HashSet<Object>(groupsBefore), new HashSet<Object>(groupsAfter));
    }
}
