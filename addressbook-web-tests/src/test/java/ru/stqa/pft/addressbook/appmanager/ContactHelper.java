package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

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

    public void selectFirstContact() {
        click(By.xpath(".//*[@id='maintable']/tbody/tr[2]/td[1]/input"));
    }

    public void deleteSelectedContract() { click(By.xpath("//input[@onclick='DeleteSel()']")); }

    public void initContactModification() { click(By.cssSelector("img[src='icons/pencil.png']")); }

    public void submitContactModification() { click(By.name("update")); }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, FormAction.CREATION);
        submitContactCreation();

    }

    public boolean isThereAContact() {
        return isElementPresent(By.cssSelector("img[src='icons/pencil.png']"));//  /tbody/tr[2]/td[1]"));
    }
}
