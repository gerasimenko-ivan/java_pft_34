package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.navigateTo().groupPage();
        if (! app.group().isThereAGroup()) {
            app.group().createGroup(new GroupData("test", null, null));
        }
    }

    @Test
    public void testGroupDeletion() {

        List<GroupData> groupsBefore = app.group().getGroupList();
        int groupToDelete = rnd.getInt(0, groupsBefore.size() - 1);
        app.group().deleteGroup(groupToDelete);

        List<GroupData> groupsAfter = app.group().getGroupList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size() - 1);

        groupsBefore.remove(groupToDelete);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }

}
