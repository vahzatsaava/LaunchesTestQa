package framework.core.pages;
import framework.core.base_entities.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LaunchesPage extends BasePage{
    private final By launchRows = By.xpath("//div[contains(@class,'gridRow')]");
    private final By refreshBtn = By.xpath("//button[contains(.,'Refresh')]");
    private final By addFilterBtn = By.xpath("//button[contains(.,'Add filter')]");
    private final By filterInput = By.xpath("//input[@placeholder='Enter name']");
    private final By firstRowNameLink = By.xpath(".//a[contains(@class,'name')]");

    // Лоадер (когда таблица обновляется)
    private final By loaderMask = By.cssSelector("[class*='spinningSquare'], [class*='spinner']");


    /** Ожидаем загрузку таблицы */
    public void waitForLaunchTable() {
        Waits.presenceOfAll(launchRows);
    }

    /** Количество строк */
    public int getLaunchesCount() {
        return Waits.presenceOfAll(launchRows).size();
    }

    /**
     * Ожидаем обновления таблицы ПО СРАВНЕНИЮ ПЕРВОЙ СТРОКИ.
     * Это 100% стабильно для React-приложений.
     */
    public void waitForLaunchTableUpdated(int oldCount) {
        String oldFirstRow = getFirstRowText();

        clickRefresh();

        waitForLoader();
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(d -> {
                    String newRow = getFirstRowText();
                    return !newRow.equals(oldFirstRow);
                });
    }

    /** Получаем текст первой строки таблицы */
    private String getFirstRowText() {
        List<WebElement> rows = Waits.presenceOfAll(launchRows);
        if (rows.isEmpty()) return "";
        return rows.get(0).getText();
    }

    /** Ждём исчезновения лоадера */
    private void waitForLoader() {
        try {
            Waits.presenceOfAll(loaderMask);
            Waits.invisible(loaderMask);
        } catch (Exception ignored) {
        }
    }

    public void clickAddFilter() {
        click(addFilterBtn);
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
