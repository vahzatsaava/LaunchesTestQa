package framework.tests.ui;

import framework.core.driver.BaseTest;
import framework.core.driver.DriverManager;
import framework.pages.LaunchesPage;
import framework.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTests extends BaseTest {
    @Test
    public void loginSuccess() {
        WebDriver driver = DriverManager.getDriver();
        driver.get("https://demo.reportportal.io/ui/#login");

        LoginPage loginPage = new LoginPage();
        loginPage.login("", "");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("default_personal"));

        Assert.assertTrue(driver.getCurrentUrl().contains("default_personal"),
                "Login failed: user was not redirected to personal area");
    }
}
