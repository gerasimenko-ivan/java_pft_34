package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.model.GroupData.ALL;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        StringBuilder xml = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
            String line = reader.readLine();
            while (line != null) {
                xml.append(line);
                line = reader.readLine();
            }
        }
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml.toString());
        return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }

    @BeforeClass
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.navigateTo().groupPage();
            GroupData groupData = new GroupData().withName("test_group");
            app.group().create(groupData);
        }
    }
    
    @Test(dataProvider = "validContactsFromXml")
    public void testContactCreation(ContactData contact) {
        Contacts contactsBefore = app.db().contacts();
        Groups groups = app.db().groups();
        File photo = new File("src/test/resources/vinni.jpeg");
        contact = contact
                .withPhoto(photo)
                .inGroup(groups.getRandom());
        app.contact().create(contact);

        // assertions
        assertThat(app.contact().getContactCount(), equalTo(contactsBefore.size() + 1));
        Contacts contactsAfter = app.db().contacts();
        contact.withId(contactsAfter.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        assertThat(contactsAfter, equalTo(contactsBefore.withAdded(contact)));
        verifyContactListInUI();
    }

}
