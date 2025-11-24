package framework.pages;

import framework.core.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {

    @FindBy(xpath = "//input[@placeholder='Login']")
    WebElement usernameInput;

    @FindBy(xpath = "//input[@placeholder='Password']")
    WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(),'Login')]")
    WebElement loginButton;

    WebDriver driver;

    public LoginPage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(usernameInput));
    }

    public void login(String user, String pass) {
        usernameInput.sendKeys(user);
        passwordInput.sendKeys(pass);
        loginButton.click();
    }
}
