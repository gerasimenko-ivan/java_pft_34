package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

        Set<GroupData> groupsBefore = app.group().all();
        int index = rnd.getInt(0, groupsBefore.size() - 1);
        GroupData deletedGroup = app.group().getByIndex(index);

        app.group().delete(index);

        Set<GroupData> groupsAfter = app.group().all();
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() - 1));

        groupsBefore.remove(deletedGroup);
        assertThat(groupsAfter, equalTo(groupsBefore));
    }

}
