package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gis on 02.11.2016.
 */
public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    private List<ContactData> contactsCache = null;

    public enum ContactViewPageInfo {WITH_GROUP_INFO, WITHOUT_GROUP_INFO};

    public void submitContactCreation() {
        click(By.xpath("//*[@id='content']/*/input[@value='Enter']"));
        contactsCache = null;
    }

    public void fillContactForm(ContactData contactData, FormAction formAction) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        attach(By.name("photo"), contactData.getPhoto());
        type(By.name("title"), contactData.getTitle());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());

        if (formAction == FormAction.CREATION) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(find(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
        find(By.xpath("//div[@id='content']//h1[.='Edit / add address book entry']"));
    }

    public void select(int index) {
        click(By.xpath(".//*[@id='maintable']/tbody/tr[" + (2 + index) + "]/td[1]/input"));  // contact tr starts from 2
    }

    public void selectById(int id) {
        logger.info("select input by id=" + id);
        click(By.xpath(".//*/input[@id='" + id + "']"));
    }

    public void deleteSelected() {
        logger.info("submit delete");
        click(By.xpath("//input[@onclick='DeleteSel()']"));
        contactsCache = null;
    }

    public void initContactModification(int index) {
        findElements(By.cssSelector("img[src='icons/pencil.png']")).get(index).click();
    }

    private void initContactModificationById(int id) {
        find(By.xpath(".//*/a[@href='edit.php?id=" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
        contactsCache = null;
    }

    public boolean doesExist() {
        return isElementPresent(By.cssSelector("img[src='icons/pencil.png']"));
    }

    public int getContactCount() {
        return findElements(By.cssSelector("img[src='icons/pencil.png']")).size();
    }

    public List<ContactData> list() {
        if (contactsCache != null) {
            return new ArrayList<ContactData>(contactsCache);
        } else {
            contactsCache = new ArrayList<ContactData>();
            List<WebElement> elements = findElements(By.xpath("//tr[@name='entry']"));
            for (WebElement element : elements) {
                int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
                String lastname = element.findElement(By.xpath("td[2]")).getText();
                String firstname = element.findElement(By.xpath("td[3]")).getText();
                String address = element.findElement(By.xpath("td[4]")).getText();
                String allPhones = element.findElement(By.xpath("td[6]")).getText();
                String allEmails = element.findElements(By.xpath("td[5]/a")).stream()
                        .map((e) -> e.getText())
                        .collect(Collectors.joining("\n"));

                ContactData contact = new ContactData();
                contact
                        .withId(id)
                        .withFirstname(firstname)
                        .withLastname(lastname)
                        .withAddress(address)
                        .withAllPhones(allPhones)
                        .withAllEmails(allEmails);
                contactsCache.add(contact);
            }
            return contactsCache;
        }
    }

    public Contacts all() {
        return new Contacts(list());
    }

    private void gotoHomePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        click(By.linkText("home page"));
        find(By.id("maintable"));
    }

    private void viewContactById(int contactId) {
        find(By.xpath(".//*/a[@href='view.php?id=" + contactId + "']")).click();
    }

    //////////////////////////// complex methods //////////////////////////

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, FormAction.CREATION);
        submitContactCreation();
        gotoHomePage();
    }

    public void modify(int index, ContactData newContact) {
        initContactModification(index);
        fillContactForm(newContact, FormAction.MODIFICATION);
        submitContactModification();
        gotoHomePage();
    }

    public void modifyById(int oldContactId, ContactData newContact) {
        logger.info("modifing contact with id=" + oldContactId);
        logger.info("new contact {" + newContact + "}");
        initContactModificationById(oldContactId);
        fillContactForm(newContact, FormAction.MODIFICATION);
        submitContactModification();
        gotoHomePage();
    }

    public ContactData getInfoFromEditFormById(int id) {
        logger.info("getting info from edit form about contact with id=" + id);
        initContactModificationById(id);
        ContactData contact = new ContactData();
        contact
                .withId(id)
                .withFirstname(getValueAttribute(By.name("firstname")))
                .withMiddlename(getValueAttribute(By.name("middlename")))
                .withLastname(getValueAttribute(By.name("lastname")))
                .withTitle(getValueAttribute(By.name("title")))
                .withAddress(getValueAttribute(By.name("address")))
                .withHomePhone(getValueAttribute(By.name("home")))
                .withMobilePhone(getValueAttribute(By.name("mobile")))
                .withWorkPhone(getValueAttribute(By.name("work")))
                .withEmail(getValueAttribute(By.name("email")))
                .withEmail2(getValueAttribute(By.name("email2")))
                .withEmail3(getValueAttribute(By.name("email3")));
        wd.navigate().back();
        return contact;
    }

    public String getContactInfoFromViewPageById(int contactId, ContactViewPageInfo includeGroupInfo) {
        viewContactById(contactId);
        String viewContent = find(By.xpath(".//*[@id='content']")).getText();
        wd.navigate().back();
        if (includeGroupInfo == ContactViewPageInfo.WITHOUT_GROUP_INFO) {
            viewContent = viewContent.replaceAll("\\n\\n\\nMember of:.*", "");
        }
        return viewContent;
    }

}
