package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static utils.JavaUtils.getEnvPropertyconfig;

public class SeleniumUtils {

    private WebDriver driver;

    public SeleniumUtils(WebDriver driver) {
        this.driver = driver;
    }

    public static String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public static String getPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public WebElement waitForElementToBeClickable(WebElement ele, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));// sel 4.x
        return wait.until(ExpectedConditions.elementToBeClickable(ele));
    }

    public WebElement waitForElementToBeVisible(WebElement ele, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));// sel 4.x
        return wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public WebElement waitForElementVisibleWithFluentWait(WebElement ele, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class);

        return wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public void waitHardForTime(int timeInMilliseconds) throws InterruptedException {
        Thread.sleep(timeInMilliseconds);
    }

    public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void doSelectByVisibleText(WebElement ele, String text) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(getEnvPropertyconfig("explicitWait"))));
        wait.until(ExpectedConditions.elementToBeClickable(ele));
        Select select = new Select(ele);
        select.selectByVisibleText(text);
    }
}
