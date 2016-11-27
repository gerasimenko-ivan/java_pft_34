package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.navigateTo().groupPage();

        Groups groupsBefore = app.group().all();

        GroupData group = new GroupData()
                .withName("test" + rnd.getInt(0, 1000));
        app.group().create(group);

        // assertions

        Groups groupsAfter = app.group().all();
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() + 1));

        group.withId(groupsAfter.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(groupsAfter, equalTo(groupsBefore.withAdded(group)));
    }

}
