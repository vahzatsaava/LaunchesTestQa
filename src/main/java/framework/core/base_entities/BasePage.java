package framework.core.base_entities;

import framework.core.driver.DriverManager;
import framework.core.utils.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
    }

    protected void click(By locator) {
        WebElement el = Waits.clickable(locator);
        el.click();
    }

    protected void click(WebElement element) {
        WebElement el = Waits.clickable(element);
        el.click();
    }

    protected void type(By locator, String value) {
        WebElement el = Waits.visible(locator);
        el.clear();
        el.sendKeys(value);
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Step("Открываем страницу: {url}")
    public void open(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
