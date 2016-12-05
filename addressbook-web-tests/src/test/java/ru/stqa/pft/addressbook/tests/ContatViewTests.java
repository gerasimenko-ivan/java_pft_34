package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by gis on 04.12.2016.
 */
public class ContatViewTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.navigateTo().home();
        if (! app.contact().doesExist()) {
            ContactData contactData = ContactData.getWithRandomData();
            app.contact().create(contactData);
        }
    }

    @Test
    public void contactViewTest() {
        app.navigateTo().home();

        int contactId = app.contact().all().getRandom().getId();
        String contactInfoFromViewPage = app.contact().getContactInfoFromViewPageById(contactId);
        ContactData contactFromEditPage = app.contact().getInfoFromEditFormById(contactId);

        // assertions
        String l = generateVeiwPageContendBy(contactFromEditPage);
        assertThat(contactInfoFromViewPage, equalTo(l));
    }

    private String generateVeiwPageContendBy(ContactData contact) {
        StringBuilder viewPageContentBuilder = new StringBuilder();

        // Name line
        String newLine = Arrays.asList(contact.getFirstname(), contact.getMiddlename(), contact.getLastname())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining(" "));
        if (!newLine.equals("")) {
            viewPageContentBuilder.append(newLine + "\n");
        }

        // title
        if (!contact.getTitle().equals("")) {
            viewPageContentBuilder.append(contact.getTitle() + "\n");
        }

        // address
        if (!contact.getAddress().equals("")) {
            viewPageContentBuilder.append(contact.getAddress());
        }

        // empty line
        if (contact.hasPhone()) {
            viewPageContentBuilder.append("\n");
        }

        // Phones
        if (!contact.getHomePhone().equals("")) {
            viewPageContentBuilder.append("\nH: " + contact.getHomePhone());
        }
        if (!contact.getMobilePhone().equals("")) {
            viewPageContentBuilder.append("\nM: " + contact.getMobilePhone());
        }
        if (!contact.getWorkPhone().equals("")) {
            viewPageContentBuilder.append("\nW: " + contact.getWorkPhone());
        }

        // empty line
        if (contact.hasEmail()) {
            viewPageContentBuilder.append("\n");
        }

        // Emails
        if (!contact.getEmail().equals("")) {
            viewPageContentBuilder.append("\n" + contact.getEmail());
        }
        if (!contact.getEmail2().equals("")) {
            viewPageContentBuilder.append("\n" + contact.getEmail2());
        }
        if (!contact.getEmail3().equals("")) {
            viewPageContentBuilder.append("\n" + contact.getEmail3());
        }

        return viewPageContentBuilder.toString();
    }
}
