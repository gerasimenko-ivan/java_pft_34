package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by gis on 06.11.2016.
 */
public class HelperBase {
    protected WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    public enum FormAction {CREATION, MODIFICATION};

    protected void click(By locator) {
        WebElement element = findElementWithTimeout(locator, 60, TimeUnit.SECONDS);
        element.click();
    }

    protected void type(By locator, String text) {
        if (text != null) {
            WebElement element = findElementWithTimeout(locator, 60, TimeUnit.SECONDS);
            String existingText = element.getAttribute("value");
            if (! text.equals(existingText)) {
                element.clear();
                element.sendKeys(text);
            }
        }
    }

    public boolean isAlertPresent(WebDriver wd) {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    protected void find(By locator) {
        findElementWithTimeout(locator, 60, TimeUnit.SECONDS);
    }

    protected boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement findElementWithTimeout(By locator, long timeout, TimeUnit timeUnit) {
        wd.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        WebElement element = wd.findElement(locator);
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        return element;
    }

}
