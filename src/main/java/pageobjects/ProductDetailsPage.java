package pageobjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Constants;
import utils.LoggerUtils;
import utils.SeleniumUtils;


public class ProductDetailsPage extends BasePage {

    private static final Logger logger = LoggerUtils.getLogger(ProductDetailsPage.class);
    private SeleniumUtils seleutil;
    @FindBy(css = "div.product-info-main span")
    private WebElement productHeader;

    @FindBy(css = "button[title='Add to Cart'] span")
    private WebElement buttonAddToCart;

    @FindBy(css = "a[class*='action showcart'] span[class*='counter qty']")
    private WebElement iconHeaderCart;

    @FindBy(css = "span[data-bind*='View and Edit Cart']")
    private WebElement linkViewEditCart;

    @FindBy(css = "div[role='alert'] div[class*='success message'] div")
    private WebElement addToCartSuccessMessage;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        seleutil = new SeleniumUtils(driver);
    }

    public String getProductHeaderText() {
        return productHeader.getText().trim();
    }

    public void selectProductSize(String size) {
        logger.info("Action: selectProductSize");
        driver.findElement(By.xpath("//div[contains(@id,'option-label-size')][text()='" + size + "']")).click();
    }

    public void selectProductColour(String colour) {
        logger.info("Action: selectProductColour");
        driver.findElement(By.xpath("//div[contains(@id,'option-label-color')][@aria-label='" + colour + "']")).click();
    }

    public void clickAddToCart() throws InterruptedException {
        logger.info("Action: clickAddToCart");
        buttonAddToCart.click();
        seleutil.waitHardForTime(5000);
    }

    public boolean isAddToCartSuccessMessageDisplayed() {
        if (getProductAddedToCartSuccessMessg().contains(Constants.ADD_TO_CART_SUCCESS_MESSG)) {
            return true;
        }
        return false;
    }

    public String getProductAddedToCartSuccessMessg() {
        return seleutil.waitForElementToBeClickable(addToCartSuccessMessage, Constants.DEFAULT_TIME_OUT).getText();
    }

    public void clickCartIcon() throws InterruptedException {
        logger.info("Action: clickCartIcon");
        seleutil.waitHardForTime(5000);
        seleutil.waitForElementToBeVisible(iconHeaderCart, Constants.DEFAULT_TIME_OUT);
        seleutil.waitForElementToBeClickable(iconHeaderCart, Constants.DEFAULT_TIME_OUT);
        iconHeaderCart.click();
    }

    public void clickViewEditCart() throws InterruptedException {
        logger.info("Action: clickViewEditCart");
        seleutil.waitForElementVisibleWithFluentWait(linkViewEditCart,Constants.DEFAULT_TIME_OUT,Constants.DEFAULT_POLLING_TIME);
        seleutil.waitForElementToBeClickable(linkViewEditCart, Constants.DEFAULT_TIME_OUT);
        linkViewEditCart.click();
    }

    public void openCartPage() throws InterruptedException {
        logger.info("Action: openCartPage");
        clickCartIcon();
        clickViewEditCart();
    }

    public void selectProductAttributes(String productsize, String productColour) {
        logger.info("Action: selectProductAttributes");
        selectProductSize(productsize);
        selectProductColour(productColour);
    }
}
