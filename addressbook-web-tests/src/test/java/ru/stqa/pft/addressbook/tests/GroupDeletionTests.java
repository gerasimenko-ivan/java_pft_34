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
        if (! app.group().doesExist()) {
            app.group().create(new GroupData("test", null, null));
        }
    }

    @Test
    public void testGroupDeletion() {

        List<GroupData> groupsBefore = app.group().list();
        int index = rnd.getInt(0, groupsBefore.size() - 1);
        app.group().delete(index);

        List<GroupData> groupsAfter = app.group().list();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size() - 1);

        groupsBefore.remove(index);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }

}
