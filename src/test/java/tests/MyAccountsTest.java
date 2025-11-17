package tests;

import BaseComponents.BaseTest;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.CreateAccountPage;
import pageobjects.HomePage;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import utils.Constants;
import utils.ExcelUtil;
import utils.JavaUtils;
import utils.LoggerUtils;

import java.io.IOException;

import static utils.JavaUtils.getRandomEmail;

public class MyAccountsTest extends BaseTest {
    private static final Logger logger = LoggerUtils.getLogger(MyAccountsTest.class);
    JavaUtils javaUtils = new JavaUtils();

    @DataProvider
    public Object[][] getSignupData() {
        Object regData[][] = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
        return regData;
    }

    @DataProvider
    public Object[][] getSignInData() {
        Object productData[][] = ExcelUtil.getTestData(Constants.LOGIN_SHEET_NAME);
        return productData;
    }

    @Test(groups = {"regression","smoke"}, dataProvider = "getSignupData",priority = 0)
    public void userRegistrationTest(String firstName, String lastName, String password) {
        logger.info("User signup test");
        HomePage homepage = new HomePage(driver);
        homepage.clickCreateAccount();
        CreateAccountPage createAccountPage = new CreateAccountPage(driver);
        Assert.assertTrue(createAccountPage
                .accountRegistration(firstName, lastName, getRandomEmail(), password), "Account couldnot be created");
        logger.info("User signup completed");
    }

    @Test(groups = {"regression"}, dataProvider = "getSignInData" ,priority = 1)
    public void myAccountUITest(String username, String password) throws IOException {
        logger.info("MyAccount UI test");
        HomePage homepage = new HomePage(driver);
        homepage.clickSignInLink();
        LoginPage loginpage = new LoginPage(driver);
        loginpage.login(username, password);
        loginpage.openMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        Assert.assertTrue(myAccountPage.isMyAccountHeadingDisplayed(), "MyAccount heading is not displayed");
        Assert.assertTrue(myAccountPage.isAccountInformationDisplayed(), "AccountInformation heading is not displayed");
        logger.info("MyAccount UI test completed");
    }

    @Test(groups = {"regression"}, dataProvider = "getSignInData", priority = 2)
    public void editProfileTest(String username, String password) throws IOException {
        logger.info("Edit profile test");
        HomePage homepage = new HomePage(driver);
        homepage.clickSignInLink();
        LoginPage loginpage = new LoginPage(driver);
        loginpage.login(username, password);
        loginpage.openMyAccount();
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        myAccountPage.clickEditProfile();
        String updatedFirstName = javaUtils.getRandomString();
        String updatedLastName = javaUtils.getRandomString();
        myAccountPage.updateProfile(updatedFirstName, updatedLastName);
        Assert.assertEquals(myAccountPage.getInformationSavedSuccessMsg(), Constants.ACCOUNTS_INFORMATION_SAVED);
        Assert.assertTrue(myAccountPage.getContactInfo().contains(updatedFirstName + " " + updatedLastName), "Updated contact info is not displayed");
        logger.info("Edit profile test completed");
    }
}