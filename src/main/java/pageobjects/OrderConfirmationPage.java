package pageobjects;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.LoggerUtils;

public class OrderConfirmationPage extends BasePage {
    private static final Logger logger = LoggerUtils.getLogger(OrderConfirmationPage.class);
    @FindBy(css = "body[class*='checkout-onepage-success'] h1>span")
    WebElement confirmationMessage;

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }


}
