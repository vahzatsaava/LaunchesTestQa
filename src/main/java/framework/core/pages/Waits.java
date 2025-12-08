package framework.core.pages;

import framework.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Waits {
    private Waits(){

    }

    private static WebDriver driver() {
        return DriverManager.getDriver();
    }

    private static WebDriverWait webDriverWait() {
        return new WebDriverWait(driver(), Duration.ofSeconds(20));
    }

    public static WebElement visible(By locator) {
        return webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(By locator) {
        return webDriverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static List<WebElement> presenceOfAll(By locator) {
        return webDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static Boolean invisible(By locator) {
        return webDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void urlContains(String fragment) {
        webDriverWait().until(ExpectedConditions.urlContains(fragment));
    }
}

