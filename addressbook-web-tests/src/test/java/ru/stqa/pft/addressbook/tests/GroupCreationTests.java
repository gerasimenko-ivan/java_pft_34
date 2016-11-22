package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        app.getNavigationHelper().isOnGroupPage();

        List<GroupData> groupsBefore = app.getGroupHelper().getGroupList();

        GroupData group = new GroupData("test1", null, null);
        app.getGroupHelper().createGroup(group);

        List<GroupData> groupsAfter = app.getGroupHelper().getGroupList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size() + 1);


        int max = 0;
        for (GroupData groupItem : groupsAfter) {
            if (groupItem.getId() > max) {
                max = groupItem.getId();
            }
        }
        group.setId(max);
        groupsBefore.add(group);
        Assert.assertEquals(new HashSet<Object>(groupsBefore), new HashSet<Object>(groupsAfter));
    }

}
