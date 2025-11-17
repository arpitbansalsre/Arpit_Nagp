package BaseComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import utils.JavaUtils;

import java.io.IOException;
import java.time.Duration;

import static utils.JavaUtils.getEnvPropertyconfig;

public class DriverFactory {

    private static InheritableThreadLocal<WebDriver> driverThreadLocal = new InheritableThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    public static void initiateDriver() throws IOException {
        WebDriver driver = null;
        if (driver == null) {
            String browser = new JavaUtils().getEnvPropertyconfig("browserName").toLowerCase();

            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported.");
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(getEnvPropertyconfig("implicitWait"))));
            driver.manage().window().maximize();
        }
        driverThreadLocal.set(driver);
    }
    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
