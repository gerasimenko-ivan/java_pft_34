package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;

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
        Groups groupsBefore = app.group().all();
        int index = rnd.getInt(0, groupsBefore.size() - 1);
        GroupData groupOld = new ArrayList<GroupData>(groupsBefore).get(index);
        GroupData groupNew = new GroupData()
                .withId(groupOld.getId())
                .withName("new-test-" + rnd.getInt(1001, 2000))
                .withHeader("new-test2")
                .withFooter("new-test3");

        app.group().modifyById(groupOld.getId(), groupNew);

        Groups groupsAfter = app.group().all();
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size()));
        assertThat(groupsAfter, equalTo(groupsBefore.withModified(groupOld, groupNew)));
    }
}
