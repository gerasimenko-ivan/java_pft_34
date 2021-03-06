package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        StringBuilder xml = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))) {
            String line = reader.readLine();
            while (line != null) {
                xml.append(line);
                line = reader.readLine();
            }
        }

        XStream xStream = new XStream();
        xStream.processAnnotations(GroupData.class);
        List<GroupData> groups = (List<GroupData>) xStream.fromXML(xml.toString());
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
            String line = reader.readLine();
            while (line != null) {
                json.append(line);
                line = reader.readLine();
            }
        }

        Gson gson = new Gson();
        List<GroupData> groups = (List<GroupData>) gson.fromJson(json.toString(), new TypeToken<List<GroupData>>(){}.getType());
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validGroupsFromXml")
    public void testGroupCreation(GroupData group) {
        app.navigateTo().groupPage();
        Groups groupsBefore = app.db().groups();

        app.group().create(group);

        // assertions
        assertThat(app.group().getGroupCount(), equalTo(groupsBefore.size() + 1));
        Groups groupsAfter = app.db().groups();
        group.withId(groupsAfter.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(groupsAfter, equalTo(groupsBefore.withAdded(group)));
        verifyGroupListInUI();
    }

    @Test
    public void testBadGroupCreation() {
        app.navigateTo().groupPage();
        Groups groupsBefore = app.db().groups();

        GroupData group = new GroupData()
                .withName("test'" + rnd.getInt(0, 1000));
        app.group().create(group);

        // assertions
        assertThat(app.group().getGroupCount(), equalTo(groupsBefore.size()));
        Groups groupsAfter = app.db().groups();
        assertThat(groupsAfter, equalTo(groupsBefore));
        verifyGroupListInUI();
    }

}
