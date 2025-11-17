package pageobjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Constants;
import utils.LoggerUtils;
import utils.SeleniumUtils;

import java.util.List;

public class SearchResultsPage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(SearchResultsPage.class);
    private SeleniumUtils seleutil;

    // Locators
    private By products = By.cssSelector("ol[class*='product-items'] a.product-item-link");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        seleutil = new SeleniumUtils(driver);
    }

    // Actions
    public void selectProduct(String mainProductName) {
        logger.info("Action: selectProduct");
        List<WebElement> productList;
        try {
            productList = seleutil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT);
        } catch (Exception e) {
            productList = seleutil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT);
        }
        for (WebElement e : productList) {
            String text = e.getText().trim();
            if (text.equals(mainProductName)) {
                e.click();
                break;
            }
        }
    }
}
