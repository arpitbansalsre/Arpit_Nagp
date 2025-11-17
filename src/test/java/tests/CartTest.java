package tests;

import BaseComponents.BaseTest;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.CartPage;
import pageobjects.HomePage;
import pageobjects.ProductDetailsPage;
import pageobjects.SearchResultsPage;
import utils.Constants;
import utils.ExcelUtil;
import utils.LoggerUtils;

import java.io.IOException;

public class CartTest extends BaseTest {

    private static final Logger logger = LoggerUtils.getLogger(CartTest.class);

    @DataProvider
    public Object[][] getProductData() {
        Object productData[][] = ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
        return productData;
    }

    @Test(groups = {"regression"}, dataProvider = "getProductData", priority = 0)
    public void addProductToCartTest(String productName, String mainProductName, String productsize, String productColour) throws InterruptedException, IOException {
        logger.info("Adding product to cart");
        HomePage homepage = new HomePage(driver);
        homepage.searchProduct(productName);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.selectProduct(mainProductName);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectProductAttributes(productsize, productColour);
        productDetailsPage.clickAddToCart();
        Assert.assertTrue(productDetailsPage.isAddToCartSuccessMessageDisplayed(), "AddToCart success message is not displayed");
        logger.info("Product to cart successfully");
    }

    @Test(groups = {"regression"}, dataProvider = "getProductData", priority = 1)
    public void viewCartTest(String productName, String mainProductName, String productsize, String productColour) throws InterruptedException, IOException {
        logger.info("Adding product to cart");
        HomePage homepage = new HomePage(driver);
        homepage.searchProduct(productName);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.selectProduct(mainProductName);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectProductAttributes(productsize, productColour);
        productDetailsPage.clickAddToCart();
        logger.info("Opening cart page");
        productDetailsPage.clickCartIcon();
        productDetailsPage.clickViewEditCart();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageOpened());
        logger.info("Verifying products added to cart");
        Assert.assertTrue(cartPage.VerifyProductDisplayedOnCart(mainProductName), "Product not displayed on cart");
    }

    @Test(groups = {"regression"}, dataProvider = "getProductData", priority = 2)
    public void editCartTest(String productName, String mainProductName, String productsize, String productColour) throws InterruptedException, IOException {
        logger.info("verifying edit cart functionality");
        HomePage homepage = new HomePage(driver);
        homepage.searchProduct(productName);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.selectProduct(mainProductName);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        productDetailsPage.selectProductAttributes(productsize, productColour);
        productDetailsPage.clickAddToCart();
        productDetailsPage.openCartPage();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartPageOpened());
        cartPage.VerifyProductDisplayedOnCart(mainProductName);
        logger.info("Removing  products from cart");
        cartPage.removeAllProductsfromCart();
        Assert.assertTrue(cartPage.verifyEmptyCart(), "Cart is not empty");
        logger.info("Product removed from cart successfully");
    }

}
