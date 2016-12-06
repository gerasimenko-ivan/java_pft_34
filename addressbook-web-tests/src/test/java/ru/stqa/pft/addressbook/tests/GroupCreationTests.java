package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroups() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }

        XStream xStream = new XStream();
        xStream.processAnnotations(GroupData.class);
        List<GroupData> groups = (List<GroupData>) xStream.fromXML(xml);
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validGroups")
    public void testGroupCreation(GroupData group) {
        app.navigateTo().groupPage();
        Groups groupsBefore = app.group().all();

        app.group().create(group);

        // assertions
        assertThat(app.group().getGroupCount(), equalTo(groupsBefore.size() + 1));
        Groups groupsAfter = app.group().all();
        group.withId(groupsAfter.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(groupsAfter, equalTo(groupsBefore.withAdded(group)));
    }

    @Test
    public void testBadGroupCreation() {
        app.navigateTo().groupPage();
        Groups groupsBefore = app.group().all();

        GroupData group = new GroupData()
                .withName("test'" + rnd.getInt(0, 1000));
        app.group().create(group);

        // assertions
        assertThat(app.group().getGroupCount(), equalTo(groupsBefore.size()));
        Groups groupsAfter = app.group().all();
        assertThat(groupsAfter, equalTo(groupsBefore));
    }

}
