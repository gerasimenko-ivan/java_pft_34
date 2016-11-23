package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        app.getNavigationHelper().isOnGroupPage();

        List<GroupData> groupsBefore = app.getGroupHelper().getGroupList();

        GroupData group = new GroupData("test2", null, null);
        app.getGroupHelper().createGroup(group);
        app.getNavigationHelper().returnToGroupPage();

        List<GroupData> groupsAfter = app.getGroupHelper().getGroupList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size() + 1);

        group.setId(groupsAfter.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        groupsBefore.add(group);
        Assert.assertEquals(new HashSet<Object>(groupsBefore), new HashSet<Object>(groupsAfter));
    }

}
