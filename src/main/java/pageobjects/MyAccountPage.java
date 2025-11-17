package pageobjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Constants;
import utils.LoggerUtils;
import utils.SeleniumUtils;

public class MyAccountPage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(MyAccountPage.class);
    private SeleniumUtils seleutil;
    // Locators
    @FindBy(css = "h1.page-title")
    private WebElement headingMyAccount;

    @FindBy(css = "div.block-dashboard-info div.block-title")
    private WebElement labelAccountInformation;

    @FindBy(css = "a[href*='/account/edit/']")
    private WebElement linkEditProfile;

    @FindBy(id = "firstname")
    private WebElement inputFirstname;

    @FindBy(id = "lastname")
    private WebElement inputLastname;

    @FindBy(css = "button[title='Save']")
    private WebElement buttonSave;

    @FindBy(css = "div.box-content p")
    private WebElement paragraphAccountInfo;

    @FindBy(css = "div[role='alert'] div[class*='success message'] div")
    private WebElement sucessMessg;

    public MyAccountPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        seleutil = new SeleniumUtils(driver);
    }

    // Actions
    public boolean isMyAccountHeadingDisplayed() {
        if (headingMyAccount.getText().contains(Constants.ACCOUNTS_PAGE_TITLE)) {
            return true;
        }
        return false;
    }

    public boolean isAccountInformationDisplayed() {
        if (labelAccountInformation.getText().contains(Constants.ACCOUNTS_INFORMATION_HEADING)) {
            return true;
        }
        return false;
    }

    public void clickEditProfile() {
        logger.info("Action: clickEditProfile");
        linkEditProfile.click();
    }

    public void updateProfile(String firstname, String lastname) {
        logger.info("Action: updateProfile");
        inputFirstname.clear();
        inputFirstname.sendKeys(firstname);
        inputLastname.clear();
        inputLastname.sendKeys(lastname);
        buttonSave.click();
    }

    public String getInformationSavedSuccessMsg() {
        return seleutil.waitForElementToBeVisible(sucessMessg, Constants.DEFAULT_TIME_OUT).getText();
    }

    public String getContactInfo() {
        return paragraphAccountInfo.getText();
    }
}