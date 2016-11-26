package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.Set;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.navigateTo().groupPage();
        if (! app.group().doesExist()) {
            app.group().create(new GroupData().withName("test"));
        }
    }

    @Test
    public void testGroupDeletion() {

        Set<GroupData> groupsBefore = app.group().hashSet();
        GroupData deletedGroup = groupsBefore.iterator().next();
        //int index = rnd.getInt(0, groupsBefore.size() - 1);       // return to this method later
        app.group().delete(deletedGroup);

        Set<GroupData> groupsAfter = app.group().hashSet();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size() - 1);

        groupsBefore.remove(deletedGroup);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }

}
