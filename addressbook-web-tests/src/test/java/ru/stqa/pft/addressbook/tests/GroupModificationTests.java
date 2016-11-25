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
        if (! app.group().doesExist()) {
            app.group().create(new GroupData("test", null, null));
        }
    }

    @Test
    public void testGroupModification() {
        List<GroupData> groupsBefore = app.group().getList();
        int index = rnd.getInt(0, groupsBefore.size() - 1);
        GroupData group = new GroupData(groupsBefore.get(index).getId(), "new-test" + rnd.getInt(1001, 2000), "new-test2", "new-test3");

        app.group().modifyGroup(index, group);

        List<GroupData> groupsAfter = app.group().getList();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size());

        groupsBefore.remove(index);
        groupsBefore.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        groupsBefore.sort(byId);
        groupsAfter.sort(byId);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }
}
