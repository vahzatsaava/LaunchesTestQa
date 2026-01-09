package framework.core.pages;

import framework.core.base_entities.BasePage;
import framework.core.utils.Waits;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {


    private final By loginField = By.cssSelector("input[name='login']");
    private final By passwordField = By.cssSelector("input[name='password']");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By launchesMenu = By.xpath("//a[contains(@href,'launches')]");

    public void login(String login, String password) {

        // Если не на странице логина — пропускаем
        if (!driver.getCurrentUrl().contains("login")) {
            return;
        }

        // Заполняем форму
        type(loginField, login);
        type(passwordField, password);

        jsClick(Waits.clickable(loginButton));

        // Ждём исчезновения логин-поля
        Waits.invisible(loginField);

        // Ждём появления меню Launches — значит вход выполнен
        Waits.visible(launchesMenu);
    }

}
