package ru.stqa.pft.addressbook;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;

public class ContactCreationTests extends TestBase {
    
    @Test
    public void testContactCreation() {
        initContactCreation();
        fillContactForm(new ContactData("John", "W.", "Doe", "Dr.", "221B Baker Street London", "9(2131)324-33-33", "test@test.ts"));
        submitContactCreation();
        gotoHomePage();
    }

}
