package pageobjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Constants;
import utils.LoggerUtils;
import utils.SeleniumUtils;

public class CreateAccountPage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(CreateAccountPage.class);
    private SeleniumUtils seleutil;
    // Locators
    @FindBy(id = "firstname")
    private WebElement inputFirstname;

    @FindBy(id = "lastname")
    private WebElement inputLastname;

    @FindBy(id = "email_address")
    private WebElement inputEmail;

    @FindBy(id = "password")
    private WebElement inputPassword;

    @FindBy(id = "password-confirmation")
    private WebElement inputConfirmPassword;

    @FindBy(css = "button[title='Create an Account']")
    private WebElement buttonCreateAccount;

    @FindBy(css = "div[role='alert'] div[class*='success message'] div")
    private WebElement sucessMessg;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        seleutil = new SeleniumUtils(driver);
    }

    public boolean accountRegistration(String firstName, String lastName, String email, String password) {
        logger.info("Action: accountRegistration");
        inputFirstname.sendKeys(firstName);
        inputLastname.sendKeys(lastName);
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        inputConfirmPassword.sendKeys(password);
        buttonCreateAccount.click();
        if (getAccountRegisterSuccessMessg().contains(Constants.REGISTER_SUCCESS_MESSG)) {
            return true;
        }
        return false;
    }

    public String getAccountRegisterSuccessMessg() {
        return seleutil.waitForElementToBeClickable(sucessMessg, Constants.DEFAULT_TIME_OUT).getText();
    }
}
