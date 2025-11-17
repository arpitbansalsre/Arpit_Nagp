package pageobjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.locators.RelativeLocator;
import utils.Constants;
import utils.LoggerUtils;
import utils.SeleniumUtils;

import java.io.IOException;

import static utils.JavaUtils.getEnvPropertyconfig;

public class HomePage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(HomePage.class);
    private SeleniumUtils seleutil;
    // Locators
    @FindBy(css = "li.authorization-link a[href*='account/login']")
    private WebElement linkSignIn;

    @FindBy(css = "a[href*='account/create']")
    private WebElement linkCreateAccount;

    @FindBy(id = "search")
    private WebElement inputSearchBox;

    @FindBy(css = "button[title='Search']")
    private WebElement buttonSearch;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        seleutil = new SeleniumUtils(driver);
    }

    // Actions
    public void clickSignInLink() {
        seleutil.waitForElementToBeClickable(linkSignIn,Constants.DEFAULT_TIME_OUT);
        linkSignIn.click();
    }

    public void clickCreateAccount() {
        seleutil.waitForElementToBeClickable(linkCreateAccount,Constants.DEFAULT_TIME_OUT);
        linkCreateAccount.click();
    }

    public boolean isSignInLinkDisplayed() {
        WebElement linkSignInRelative = driver.findElement(RelativeLocator.with(By.cssSelector("a[href*='account/login']")).toLeftOf(driver.findElement(By.cssSelector("a[href*='account/create']"))));
        return linkSignInRelative.isDisplayed();
    }

    public boolean isCreateAccountLinkDisplayed() {
        WebElement linkCreateAccountRelative = driver.findElement(RelativeLocator.with(By.cssSelector("a[href*='account/create']")).toRightOf(driver.findElement(By.cssSelector("li.authorization-link a[href*='account/login']"))));
        return linkCreateAccountRelative.isDisplayed();
    }

    public boolean isSearchBoxDisplayed() {
        return inputSearchBox.isDisplayed();
    }

    public void searchProduct(String productName) throws InterruptedException, IOException {
        logger.info("Action: searchProduct");
        if (isSearchBoxDisplayed()) {
            inputSearchBox.sendKeys(productName);
            seleutil.waitHardForTime(4000);
            seleutil.waitForElementToBeClickable(buttonSearch, Integer.parseInt(getEnvPropertyconfig("explicitWait")));
            buttonSearch.click();
        }

    }
}