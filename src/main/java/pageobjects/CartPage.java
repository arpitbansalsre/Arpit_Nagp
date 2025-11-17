package pageobjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Constants;
import utils.LoggerUtils;
import utils.SeleniumUtils;

import java.util.List;

public class CartPage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(CartPage.class);
    private SeleniumUtils sseleutil;

    @FindBy(css = "h1.page-title span")
    private WebElement titleCartPage;

    @FindBy(css = "table#shopping-cart-table div.product-item-details a")
    private List<WebElement> cartProducts;

    @FindBy(css = "div.cart-summary button[title='Proceed to Checkout'] span")
    private WebElement buttonProceedToCheckout;

    @FindBy(id = "top-cart-btn-checkout")
    private WebElement minicartProceedToCheckout;

    @FindBy(css = "tbody[class*='cart item'] a[title='Remove item']")
    private List<WebElement> removeProduct;

    @FindBy(css = "div.cart-empty p")
    private WebElement emptyCartMessage;


    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        sseleutil = new SeleniumUtils(driver);

    }

    public Boolean VerifyProductDisplayedOnCart(String productName) {
        Boolean match = cartProducts.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
        return match;
    }

    public void goToCheckout() {
        logger.info("Action: goToCheckout");
        sseleutil.waitForElementToBeClickable(buttonProceedToCheckout, Constants.DEFAULT_TIME_OUT);
        buttonProceedToCheckout.click();
    }

    public boolean isCartPageOpened() {
        sseleutil.waitForElementToBeVisible(titleCartPage, Constants.DEFAULT_TIME_OUT);
        if (titleCartPage.getText().contains(Constants.TITLE_CART_PAGE)) {
            return true;
        }
        logger.error("Cart page not opened");
        return false;
    }

    public void removeAllProductsfromCart() {
        logger.info("Action: removeAllProductsfromCart");
        removeProduct.stream().forEach(WebElement::click);
    }

    public boolean verifyEmptyCart() {
        if (emptyCartMessage.getText().contains(Constants.EMPTY_CART_MESSAGE)) {
            return true;
        }
        logger.error("Cart items could not be removed");
        return false;
    }

    public void clickMiniCartCheckout() {
        logger.info("Action: clickMiniCartCheckout");
        sseleutil.waitForElementToBeClickable(minicartProceedToCheckout, Constants.DEFAULT_TIME_OUT);
        minicartProceedToCheckout.click();
    }
}
