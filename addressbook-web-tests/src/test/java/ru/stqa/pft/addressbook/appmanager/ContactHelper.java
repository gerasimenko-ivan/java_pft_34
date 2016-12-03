package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gis on 02.11.2016.
 */
public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    private List<ContactData> contactsCache = null;

    public void submitContactCreation() {
        click(By.xpath("//*[@id='content']/*/input[@value='Enter']"));
        contactsCache = null;
    }

    public void fillContactForm(ContactData contactData, FormAction formAction) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());

        if (formAction == FormAction.CREATION) {
            if (contactData.getGroup() != null) {
                new Select(findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
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
        click(By.xpath(".//*/input[@id='" + id + "']"));
    }

    public void deleteSelected() {
        click(By.xpath("//input[@onclick='DeleteSel()']"));
        contactsCache = null;
    }

    public void initContactModification(int index) {
        findElements(By.cssSelector("img[src='icons/pencil.png']")).get(index).click();
    }

    private void initContactModificationById(int id) {
        findElement(By.xpath(".//*/a[@href='edit.php?id=" + id + "']")).click();
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
                ContactData contact = new ContactData();
                contact.withId(id)
                        .withFirstname(firstname)
                        .withLastname(lastname);
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
        initContactModificationById(oldContactId);
        fillContactForm(newContact, FormAction.MODIFICATION);
        submitContactModification();
        gotoHomePage();
    }
}
