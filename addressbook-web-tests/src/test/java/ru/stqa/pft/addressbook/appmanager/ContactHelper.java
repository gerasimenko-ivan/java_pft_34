package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gis on 02.11.2016.
 */
public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, FormAction formAction) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("email"), contactData.getEmail());

        if (formAction == FormAction.CREATION) {
            if (contactData.getGroup() != null) {
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
        find(By.xpath("//div[@id='content']//h1[.='Edit / add address book entry']"));
    }

    public void selectContact(int index) {
        click(By.xpath(".//*[@id='maintable']/tbody/tr[" + (2 + index) + "]/td[1]/input"));  // contact tr starts from 2
    }

    public void deleteSelectedContract() { click(By.xpath("//input[@onclick='DeleteSel()']")); }

    public void initContactModification(int index) {
        wd.findElements(By.cssSelector("img[src='icons/pencil.png']")).get(index).click();
    }

    public void submitContactModification() { click(By.name("update")); }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, FormAction.CREATION);
        submitContactCreation();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.cssSelector("img[src='icons/pencil.png']"));
    }

    public int getContactCount() {
        return wd.findElements(By.cssSelector("img[src='icons/pencil.png']")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            String lastname = element.findElement(By.xpath("td[2]")).getText();
            String firstname = element.findElement(By.xpath("td[3]")).getText();
            ContactData contact = new ContactData();
            contact.setId(id)
                    .setFirstname(firstname)
                    .setLastname(lastname);
            contacts.add(contact);
        }
        return contacts;
    }
}
