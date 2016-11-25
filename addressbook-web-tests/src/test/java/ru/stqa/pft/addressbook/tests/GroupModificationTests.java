package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by gis on 07.11.2016.
 */
public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.navigateTo().groupPage();
        if (! app.group().isThereAGroup()) {
            app.group().createGroup(new GroupData("test", null, null));
        }
    }

    @Test
    public void testGroupModification() {
        List<GroupData> groupsBefore = app.group().getGroupList();
        int groupToEdit = rnd.getInt(0, groupsBefore.size() - 1);
        GroupData group = new GroupData(groupsBefore.get(groupToEdit).getId(), "new-test" + rnd.getInt(1001, 2000), "new-test2", "new-test3");

        app.group().modifyGroup(groupToEdit, group);

        List<GroupData> groupsAfter = app.group().getGroupList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size());

        groupsBefore.remove(groupToEdit);
        groupsBefore.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        groupsBefore.sort(byId);
        groupsAfter.sort(byId);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }
}
