package framework.core.pages;

import framework.core.base_entities.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By loginField = By.cssSelector("input[name='login']");
    private final By passwordField = By.cssSelector("input[name='password']");
    private final By loginButton = By.cssSelector("button[type='submit']");

    public void login(String login, String password) {

        type(loginField, login);
        type(passwordField, password);

        WebElement btn = Waits.clickable(loginButton);
        jsClick(btn);

        Waits.urlContains("default_personal");
    }

}
