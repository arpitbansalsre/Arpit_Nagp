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

public class LoginPage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(LoginPage.class);
    private SeleniumUtils seleutil;
    @FindBy(id = "email")
    private WebElement inputUsername;

    @FindBy(css = "input[title='Password']")
    private WebElement inputPassword;

    @FindBy(css = "button.login[id='send2']")
    private WebElement buttonLogin;

    @FindBy(css = "span.logged-in")
    private WebElement labelWelcome;

    @FindBy(css = "div[role='alert'] div div")
    private WebElement labelLoginError;

    @FindBy(css = "button[data-action='customer-menu-toggle']")
    private WebElement toggleCustomerMenu;

    @FindBy(css = "div[class='customer-menu'] a[href*='/customer/account']")
    private WebElement linkMyAccount;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        seleutil = new SeleniumUtils(driver);
    }

    // Actions
    public void enterUsername(String username) {
        logger.info("Action: enterUsername");
        inputUsername.sendKeys(username);
    }

    public void enterPassword(String password) {
        logger.info("Action: enterPassword");
        // Assuming the "username" and "password" fields are near to each other //Selenium4.x
        WebElement passwordFieldRelative = driver.findElement(RelativeLocator.with(By.tagName("input")).below(driver.findElement(By.cssSelector("input[title='Email']"))));
        passwordFieldRelative.sendKeys(password);
    }

    public void clickLoginButton() {
        logger.info("Action: clickLoginButton");
        WebElement relativebuttonLogin = driver.findElement(RelativeLocator.with(By.tagName("button")).below(driver.findElement(By.cssSelector("input[title='Password']"))));
        relativebuttonLogin.click();
    }

    public void login(String username, String password) {
        logger.info("Logging in with {} and {}", username, password);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginFormOpened() {
        return inputUsername.isDisplayed();
    }

    public boolean isWelcomeHeaderDisplayed() {
        seleutil.waitForElementToBeVisible(labelWelcome, Constants.DEFAULT_TIME_OUT);
        return labelWelcome.isDisplayed();
    }

    public boolean isLoginErrorMessageDisplayed() {
        WebElement relativeLabelLoginError = driver.findElement(RelativeLocator.with(By.cssSelector("div[role='alert'] div ")).above(driver.findElement(By.cssSelector("input[title='Email']"))));
        seleutil.waitForElementToBeVisible(relativeLabelLoginError, Constants.DEFAULT_TIME_OUT);
        String actualErrorMsg = relativeLabelLoginError.getText();
        if (relativeLabelLoginError.isDisplayed() && actualErrorMsg.contains(Constants.LOGIN_PAGE_ERROR_MESSG)) {
            return true;
        }
        return false;
    }

    public void openMyAccount() {
        logger.info("Action: openMyAccount");
        WebElement relativeToggleCustomerMenu = driver.findElement(RelativeLocator.with(By.cssSelector("button[data-action='customer-menu-toggle']")).near(driver.findElement(By.cssSelector("span.logged-in"))));
        relativeToggleCustomerMenu.click();
        linkMyAccount.click();
    }
}
