package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gis on 07.11.2016.
 */
public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.navigateTo().groupPage();
        if (! app.group().doesExist()) {
            app.group().create(new GroupData().withName("test"));
        }
    }

    @Test
    public void testGroupModification() {
        Set<GroupData> groupsBefore = app.group().hashSet();
        int index = rnd.getInt(0, groupsBefore.size() - 1);
        GroupData groupOld = app.group().getByIndex(index);
        GroupData groupNew = new GroupData()
                .withId(groupOld.getId())
                .withName("new-test-" + rnd.getInt(1001, 2000))
                .withHeader("new-test2")
                .withFooter("new-test3");

        app.group().modifyGroup(index, groupNew);

        Set<GroupData> groupsAfter = app.group().hashSet();
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size()));

        groupsBefore.remove(groupOld);
        groupsBefore.add(groupNew);
        assertThat(groupsAfter, equalTo(groupsBefore));
    }
}
