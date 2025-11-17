package BaseComponents;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.JavaUtils;
import utils.LoggerUtils;

import java.io.IOException;

import static utils.LoggerUtils.setLogFileName;

public class BaseTest {
    private static Logger logger;
    public BaseTest(){
        setLogFileName();
    }
    public WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void setupLogger() {
        logger = LoggerUtils.getLogger(BaseTest.class);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpDriver() throws IOException {
        // Initialize WebDriver and launch application
        logger.info("Initializing driver...");
        DriverFactory.initiateDriver();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        logger.info("Initializing application...");
        driver.get(new JavaUtils().getEnvPropertyconfig("testSiteURL"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Closing browser...");
        DriverFactory.quitDriver();
    }
}
