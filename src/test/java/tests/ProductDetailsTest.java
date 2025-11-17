package tests;

import BaseComponents.BaseTest;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.ProductDetailsPage;
import pageobjects.SearchResultsPage;
import utils.Constants;
import utils.ExcelUtil;
import utils.LoggerUtils;

import java.io.IOException;

public class ProductDetailsTest extends BaseTest {
    private static final Logger logger = LoggerUtils.getLogger(ProductDetailsTest.class);

    @DataProvider
    public Object[][] getProductData() {
        Object productData[][] = ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
        return productData;
    }

    @Test(groups = {"regression"}, dataProvider = "getProductData", priority = 0)
    public void productSearchTest(String productName, String mainProductName, String productsize, String productColour) throws InterruptedException, IOException {
        logger.info("Search product test");
        HomePage homepage = new HomePage(driver);
        homepage.searchProduct(productName);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.selectProduct(mainProductName);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        Assert.assertEquals(productDetailsPage.getProductHeaderText(), mainProductName);
        logger.info("Search product test completed successfully");
    }
}
