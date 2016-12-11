package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gis on 07.11.2016.
 */
public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("test"));
        }
    }

    @Test
    public void testGroupModification() {
        app.navigateTo().groupPage();
        Groups groupsBefore = app.db().groups();
        GroupData groupOld = groupsBefore.getRandom();
        GroupData groupNew = new GroupData()
                .withId(groupOld.getId())
                .withName("new-test-" + rnd.getInt(1001, 2000))
                .withHeader("new-test2")
                .withFooter("new-test3");

        app.group().modifyById(groupOld.getId(), groupNew);

        // assertions
        assertThat(app.group().getGroupCount(), equalTo(groupsBefore.size()));
        Groups groupsAfter = app.db().groups();
        assertThat(groupsAfter, equalTo(groupsBefore.withModified(groupOld, groupNew)));
    }
}
