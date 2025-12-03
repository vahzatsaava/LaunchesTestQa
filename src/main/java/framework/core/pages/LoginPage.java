package framework.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage{

    private final By loginField = By.cssSelector("input[name='login']");
    private final By passwordField = By.cssSelector("input[name='password']");
    private final By loginButton = By.cssSelector("button[type='submit']");


    public void login(String login, String password) {
        WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(loginField));
        user.clear();
        user.sendKeys(login);

        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        pass.clear();
        pass.sendKeys(password);

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        jsClick(btn);

        waitForUrlContains("default_personal");
    }


}
