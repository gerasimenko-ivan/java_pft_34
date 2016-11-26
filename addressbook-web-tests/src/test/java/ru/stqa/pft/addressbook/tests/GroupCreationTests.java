package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.navigateTo().groupPage();

        Set<GroupData> groupsBefore = app.group().hashSet();

        GroupData group = new GroupData()
                .withName("test" + rnd.getInt(0, 1000));
        app.group().create(group);

        Set<GroupData> groupsAfter = app.group().hashSet();
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() + 1));

        group.withId(groupsAfter.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        groupsBefore.add(group);
        assertThat(groupsAfter, equalTo(groupsBefore));
    }

}
