package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;

/**
 * Created by gis on 07.11.2016.
 */
public class AlertHelper extends HelperBase {

    public AlertHelper(WebDriver wd) {
        super(wd);
    }

    public void accept() {
        wd.switchTo().alert().accept();
    }
}
