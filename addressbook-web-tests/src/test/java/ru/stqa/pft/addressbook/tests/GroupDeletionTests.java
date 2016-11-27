package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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

        Groups groupsBefore = app.group().all();
        GroupData deletedGroup = groupsBefore.getRandom();

        app.group().deleteById(deletedGroup.getId());

        // assertions
        Groups groupsAfter = app.group().all();
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() - 1));
        assertThat(groupsAfter, equalTo(groupsBefore.without(deletedGroup)));
    }

}
