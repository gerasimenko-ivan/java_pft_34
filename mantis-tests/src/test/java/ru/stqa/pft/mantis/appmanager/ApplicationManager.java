package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by gis on 01.11.2016.
 */
public class ApplicationManager {
    private WebDriver wd;

    private String browser;

    private final Properties properties;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        System.setProperty("webdriver.gecko.driver", "C:\\Tools\\GeckoDriver\\geckodriver.exe");
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        if (browser.equals(BrowserType.FIREFOX)) {
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
            profile.setPreference("startup.homepage_welcome_url.additional",  "about:blank");
            wd = new FirefoxDriver(profile);
        } else if (browser.equals(BrowserType.CHROME)) {
            wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.IE)) {
            wd = new InternetExplorerDriver();
        }
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        wd.get(properties.getProperty("web.baseUrl"));

    }


    public void stop() {
        wd.quit();
    }

}
