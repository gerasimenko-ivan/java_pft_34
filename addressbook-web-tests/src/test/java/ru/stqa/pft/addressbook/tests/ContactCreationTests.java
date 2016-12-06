package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

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

public class ContactCreationTests extends TestBase {
    private String groupName = "test1";

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }

        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
        return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }

    @BeforeClass
    public void ensurePreconditions() {
        app.navigateTo().groupPage();
        if (! app.group().doesExist(groupName)) {
            GroupData groupData = new GroupData().withName(groupName);
            app.group().create(groupData);
        }
    }
    
    @Test(dataProvider = "validContactsFromXml")
    public void testContactCreation(ContactData contact) {
        app.navigateTo().home();
        Contacts contactsBefore = app.contact().all();
        File photo = new File("src/test/resources/vinni.jpeg");
        contact = contact
                .withGroup(groupName)
                .withPhoto(photo);
        app.contact().create(contact);

        // assertions
        assertThat(app.contact().getContactCount(), equalTo(contactsBefore.size() + 1));
        Contacts contactsAfter = app.contact().all();
        contact.withId(contactsAfter.stream().mapToInt((c) -> c.getId()).max().getAsInt());
        assertThat(contactsAfter, equalTo(contactsBefore.withAdded(contact)));
    }

}
