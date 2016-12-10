package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.internal.TestResult;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.appmanager.RandomDataGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by gis on 01.11.2016.
 */
public class TestBase {

    Logger logger = LoggerFactory.getLogger("");

    protected static final ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
    protected static final RandomDataGenerator rnd = new RandomDataGenerator();

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method method, Object[] params) {
        logger.info("Start test " + method.getName());
        logger.info("  params: " + Arrays.asList(params));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method method, ITestResult result) {
        if (!result.isSuccess()) {
            logger.info(result.getThrowable().getMessage());
        }
        logger.info("Stop test " + method.getName());
        logger.info("------------------------------------------------");
    }

}
