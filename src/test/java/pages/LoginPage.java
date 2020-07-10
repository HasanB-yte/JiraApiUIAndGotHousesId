package pages;

import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='login-form-username']")
    public WebElement usernameField;

    @FindBy(xpath = "//input[@id='login-form-password']")
    public WebElement passwordField;

    @FindBy(xpath = "//input[@value='Log In']")
    public WebElement loginButton;


    public void login(WebDriver driver, String url) {

            driver.get(url);
            this.usernameField.sendKeys(ConfigReader.getProperty("jiraUserName"));
            this.passwordField.sendKeys(ConfigReader.getProperty("jiraPassword"));
            this.loginButton.submit();

    }

}
