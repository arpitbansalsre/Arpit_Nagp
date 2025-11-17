package tests;

import BaseComponents.BaseTest;
import model.AddressBook;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.*;
import utils.Constants;
import utils.ExcelUtil;
import utils.JsonUtil;
import utils.LoggerUtils;

import java.io.IOException;
import java.util.List;

import static utils.JavaUtils.getRandomEmail;

public class CheckoutTest extends BaseTest {
    private static final Logger logger = LoggerUtils.getLogger(CheckoutTest.class);

    @DataProvider
    public Object[][] getProductData() {
        Object productData[][] = ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
        return productData;
    }

    @Test(groups = {"regression","smoke"}, dataProvider = "getProductData", priority = 0)
    public void submitOrderTest(String productName, String mainProductName, String productsize, String productColour) throws InterruptedException, IOException {
        logger.info("Submit order test");
        HomePage homepage = new HomePage(driver);
        homepage.searchProduct(productName);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.selectProduct(mainProductName);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectProductAttributes(productsize, productColour);
        productDetailsPage.clickAddToCart();
        productDetailsPage.openCartPage();
        CartPage cartPage = new CartPage(driver);
        cartPage.goToCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isCheckoutPageOpened(), "Checkout page not opened");
        checkoutPage.enterSignupEmail(getRandomEmail());
        List<AddressBook> shippingAddress = new JsonUtil().getAddressJsonData(Constants.ADDRESS_BOOK_DATA_FILE_PATH);
        logger.info("Fetching shipping address from json");
        AddressBook addressdata = shippingAddress.get(0);
        logger.info("Shipping address fetched");
        checkoutPage.enterShippingAddress(addressdata.getFirstname(), addressdata.getLastname(), addressdata.getCompany(), addressdata.getStreetaddress(), addressdata.getCountry(), addressdata.getState(), addressdata.getCity(), addressdata.getPostalcode(), addressdata.getPhone());
        checkoutPage.selectShippingMethod();
        checkoutPage.clickNext();
        Assert.assertTrue(checkoutPage.IsPaymentSectionLoaded(), "Payment section not loaded");
        checkoutPage.clickPlaceOrder();
        OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(driver);
        String confirmMessage = orderConfirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(Constants.ORDER_SUCCESS_MESSG), "Order confirmation message not displayed");
        logger.info("Order placed successfully");
    }

    @Test(groups = {"regression"}, dataProvider = "getProductData", priority = 1)
    public void minicartCheckoutNavigationTest(String productName, String mainProductName, String productsize, String productColour) throws InterruptedException, IOException {
        logger.info("Minicart checkout navigation test");
        HomePage homepage = new HomePage(driver);
        homepage.searchProduct(productName);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.selectProduct(mainProductName);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectProductAttributes(productsize, productColour);
        productDetailsPage.clickAddToCart();
        productDetailsPage.clickCartIcon();
        CartPage cartPage = new CartPage(driver);
        cartPage.clickMiniCartCheckout();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isCheckoutPageOpened(), "Checkout page is not opened");
        logger.info("Checkout page opened");
    }
}
