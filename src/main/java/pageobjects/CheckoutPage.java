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

public class CheckoutPage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(CheckoutPage.class);
    private SeleniumUtils seleutil;

    @FindBy(css = "table[class='table-checkout-shipping-method'] input[type='radio']")
    private WebElement radioButtonShippingMethod;

    @FindBy(id = "customer-email")
    private WebElement inputSignupEmail;

    @FindBy(css = "input[name='firstname']")
    private WebElement inputFirstname;

    @FindBy(css = "input[name='lastname']")
    private WebElement inputLastname;

    @FindBy(css = "input[name='company']")
    private WebElement inputCompany;

    @FindBy(css = "input[name='street[0]']")
    private WebElement inputStreetAddressLine1;

    @FindBy(css = "input[name='city']")
    private WebElement inputCity;

    @FindBy(css = "select[name='region_id']")
    private WebElement dropdownState;

    @FindBy(css = "input[name='postcode']")
    private WebElement inputPostalcode;

    @FindBy(css = "select[name='country_id']")
    private WebElement dropdownCountry;

    @FindBy(css = "input[name='telephone']")
    private WebElement inputPhone;

    @FindBy(xpath = "//div[@class='step-title'][text()='Payment Method']")
    private WebElement headingPaymentMethod;

    @FindBy(xpath = "//span[text()='Place Order']")
    private WebElement buttonPlaceOrder;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        seleutil = new SeleniumUtils(driver);
    }

    public void enterShippingAddress(String firstname, String lastname, String company, String streetAddress, String country, String state, String city, String zipcode, String phone) throws InterruptedException, IOException {
        logger.info("Action: enterShippingAddress");
        seleutil.waitForElementToBeClickable(inputFirstname, Constants.DEFAULT_TIME_OUT);
        inputFirstname.sendKeys(firstname);
        inputLastname.sendKeys(lastname);
        inputCompany.sendKeys(company);
        inputStreetAddressLine1.sendKeys(streetAddress);
        seleutil.doSelectByVisibleText(dropdownCountry, country);
        seleutil.waitHardForTime(2000);
        seleutil.doSelectByVisibleText(dropdownState, state);
        inputCity.sendKeys(city);
        inputPostalcode.sendKeys(zipcode);
        inputPhone.sendKeys(phone);
    }

    public boolean isCheckoutPageOpened() {
        seleutil.waitForElementToBeClickable(inputFirstname, Constants.DEFAULT_TIME_OUT);
        if (driver.getCurrentUrl().contains(Constants.URL_SHIPPING_PAGE)) {
            return true;
        }
        logger.error("Checkout page not opened");
        return false;
    }

    public void enterSignupEmail(String email) {
        logger.info("Action: enterSignupEmail");
        seleutil.waitForElementToBeClickable(inputSignupEmail, Constants.DEFAULT_TIME_OUT);
        inputSignupEmail.sendKeys(email);
    }

    public void selectShippingMethod() {
        logger.info("Action: selectShippingMethod");
        seleutil.waitForElementToBeClickable(radioButtonShippingMethod, Constants.DEFAULT_TIME_OUT);
        radioButtonShippingMethod.click();

    }

    public void clickNext() {
        logger.info("Action: clickNext");
        WebElement buttonNextRelative = driver.findElement(RelativeLocator.with(By.cssSelector("button[type='submit']")).below(driver.findElement(By.cssSelector("input[name='telephone']"))));
        seleutil.waitForElementToBeClickable(buttonNextRelative, Constants.DEFAULT_TIME_OUT);
        buttonNextRelative.click();
    }

    public boolean IsPaymentSectionLoaded() {
        seleutil.waitForElementToBeVisible(headingPaymentMethod, Constants.DEFAULT_TIME_OUT);
        return headingPaymentMethod.isDisplayed();
    }

    public void clickPlaceOrder() throws InterruptedException {
        logger.info("Action: clickPlaceOrder");
        seleutil.waitHardForTime(2000);
        seleutil.waitForElementVisibleWithFluentWait(buttonPlaceOrder, Constants.DEFAULT_TIME_OUT,Constants.DEFAULT_POLLING_TIME);
        seleutil.waitForElementToBeClickable(buttonPlaceOrder, Constants.DEFAULT_TIME_OUT);
        buttonPlaceOrder.click();

    }
}
