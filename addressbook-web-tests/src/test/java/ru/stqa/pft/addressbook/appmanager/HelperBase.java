package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by gis on 06.11.2016.
 */
public class HelperBase {
    protected WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    protected List<WebElement> findElements(By locator) {
        return wd.findElements(locator);
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

    protected void attach(By locator, File file) {
        if (file != null) {
            findElementWithTimeout(locator, 60, TimeUnit.SECONDS).sendKeys(file.getAbsolutePath());
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

    protected WebElement find(By locator) {
        return findElementWithTimeout(locator, 60, TimeUnit.SECONDS);
    }

    protected String getValueAttribute(By locator) {
        return find(locator).getAttribute("value");
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
