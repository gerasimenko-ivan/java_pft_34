package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
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
        int groupToEdit = groupsBefore.size() - 1;
        GroupData group = new GroupData(groupsBefore.get(groupToEdit).getId(), "new-test1", "new-test2", "new-test3");

        app.getGroupHelper().modifyGroup(groupToEdit, group);

        List<GroupData> groupsAfter = app.getGroupHelper().getGroupList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size());

        groupsBefore.remove(groupToEdit);
        groupsBefore.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        groupsBefore.sort(byId);
        groupsAfter.sort(byId);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }
}
