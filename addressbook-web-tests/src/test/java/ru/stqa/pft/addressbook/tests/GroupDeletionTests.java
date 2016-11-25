package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {
    
    @Test
    public void testGroupDeletion() {
        app.getNavigationHelper().gotoGroupPage();
        app.getNavigationHelper().isOnGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test", null, null));
        }

        List<GroupData> groupsBefore = app.getGroupHelper().getGroupList();
        int groupToDelete = groupsBefore.size() - 1;
        app.getGroupHelper().deleteGroup(groupToDelete);

        List<GroupData> groupsAfter = app.getGroupHelper().getGroupList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size() - 1);

        groupsBefore.remove(groupsBefore.size() - 1);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }

}
