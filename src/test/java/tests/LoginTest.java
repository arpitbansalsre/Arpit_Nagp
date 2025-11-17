package tests;

import BaseComponents.BaseTest;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import utils.Constants;
import utils.ExcelUtil;
import utils.LoggerUtils;
import utils.SeleniumUtils;

import java.io.IOException;

public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerUtils.getLogger(LoginTest.class);

    @DataProvider
    public Object[][] getLoginNegativeData() {
        return new Object[][]{
                {"testinvalid@gmail.com", "test!!!2233"}
        };
    }

    @DataProvider
    public Object[][] getLoginCredentialsData() {
        Object productData[][] = ExcelUtil.getTestData(Constants.LOGIN_SHEET_NAME);
        return productData;
    }

    @Test(groups = {"regression","smoke"}, dataProvider = "getLoginCredentialsData", priority = 0)
    public void successfulLoginTest(String username, String password) throws IOException {
        logger.info("Login test");
        HomePage homepage = new HomePage(driver);
        homepage.clickSignInLink();
        LoginPage loginpage = new LoginPage(driver);
        Assert.assertTrue(loginpage.isLoginFormOpened(), "Login form is not opened");
        loginpage.login(username, password);
        Assert.assertTrue(loginpage.isWelcomeHeaderDisplayed(), "Welcome header not displayed");
        logger.info("Login successful");
    }

    @Test(groups = {"regression"}, priority = 1)
    public void homePageUITest() {
        logger.info("Homepage UI test");
        HomePage homepage = new HomePage(driver);
        String actTitle = SeleniumUtils.getPageTitle(driver);
        System.out.println("page title : " + actTitle);
        Assert.assertEquals(actTitle, Constants.HOME_PAGE_TITLE, Constants.HOME_PAGE_TITLE_MISMATCHED);
        String url = SeleniumUtils.getPageUrl(driver);
        System.out.println("login page url : " + url);
        Assert.assertTrue(url.contains(Constants.HOME_PAGE_FRACTION_URL), "Expected Url not displayed");
        Assert.assertTrue(homepage.isSignInLinkDisplayed(), "SignIn link not displayed");
        Assert.assertTrue(homepage.isCreateAccountLinkDisplayed(), "Create account link not displayed");
        logger.info("Homepage UI test completed");
    }

    @Test(groups = {"regression", "smoke"}, dataProvider = "getLoginNegativeData", priority = 2)
    public void unsuccessfulLoginTest(String username, String password) {
        logger.warn("NegativeTest: Unsuccessful login scenario");
        HomePage homepage = new HomePage(driver);
        homepage.clickSignInLink();
        LoginPage loginpage = new LoginPage(driver);
        loginpage.login(username, password);
        logger.error("NegativeTest: Test has been explicitly failed to check screenshotonfailure feature");
        Assert.assertTrue(loginpage.isLoginErrorMessageDisplayed(), "Login error message is not displayed");
    }
}