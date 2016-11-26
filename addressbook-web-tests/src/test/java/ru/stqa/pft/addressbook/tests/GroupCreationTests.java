package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.navigateTo().groupPage();

        Set<GroupData> groupsBefore = app.group().hashSet();

        GroupData group = new GroupData()
                .withName("test" + rnd.getInt(0, 1000));
        app.group().create(group);

        Set<GroupData> groupsAfter = app.group().hashSet();
        Assert.assertEquals(groupsAfter.size(), groupsBefore.size() + 1);

        group.withId(groupsAfter.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        groupsBefore.add(group);
        Assert.assertEquals(groupsBefore, groupsAfter);
    }

}
