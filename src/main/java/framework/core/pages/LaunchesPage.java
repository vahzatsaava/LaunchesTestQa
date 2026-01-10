package framework.core.pages;
import framework.core.base_entities.BasePage;
import framework.core.utils.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LaunchesPage extends BasePage{
    private final By launchRows = By.xpath("//div[contains(@class,'gridRow')]");
    private final By refreshBtn = By.xpath("//button[contains(.,'Refresh')]");
    private final By addFilterBtn = By.xpath("//button[contains(.,'Add filter')]");
    private final By filterInput = By.xpath("//input[@placeholder='Enter name']");
    private final By firstRowNameLink = By.xpath(".//a[contains(@class,'name')]");
    private final By loaderMask = By.cssSelector("[class*='spinningSquare'], [class*='spinner']");

    /** Ждём появление таблицы */
    public void waitForLaunchTable() {
        Waits.presenceOfAll(launchRows);
    }

    /** Количество строк */
    public int getLaunchesCount() {
        return Waits.presenceOfAll(launchRows).size();
    }

    /** Лучший вариант для свежего DOM RP */
    public void waitForLaunchTableUpdated() {
        waitForLoader();
        waitForLaunchTable();
    }

    /** Универсальная обработка loader */
    private void waitForLoader() {
        WebDriverWait waitShort = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            waitShort.until(ExpectedConditions.presenceOfAllElementsLocatedBy(loaderMask));
        } catch (Exception ignored) {}

        try {
            waitLong.until(ExpectedConditions.invisibilityOfElementLocated(loaderMask));
        } catch (Exception ignored){}
    }


    public void enterFilterText(String text) {
        type(filterInput, text);
        waitForLoader();
        waitForLaunchTable();
    }

    public void clickRefresh() {
        click(refreshBtn);
    }

    public void openFirstLaunch() {
        List<WebElement> rows = Waits.presenceOfAll(launchRows);
        WebElement link = rows.get(0).findElement(firstRowNameLink);
        jsClick(link);
    }

    public String getFirstLaunchId() {
        List<WebElement> rows = Waits.presenceOfAll(launchRows);
        WebElement link = rows.get(0).findElement(firstRowNameLink);

        String href = link.getAttribute("href");
        return href.substring(href.lastIndexOf("/") + 1);
    }
}
