package framework.core.steps;

import framework.core.driver.DriverManager;
import framework.core.pages.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginSteps {

    @Step("Авторизация пользователя в системе")
    public static void login() {
        WebDriver driver = DriverManager.getDriver();

        driver.get("https://demo.reportportal.io/ui/#login");
        LoginPage loginPage = new LoginPage();

        loginPage.login("default", "1q2w3e");

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("default_personal"));
    }
}
