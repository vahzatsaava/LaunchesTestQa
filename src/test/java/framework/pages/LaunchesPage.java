package framework.pages;

import framework.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LaunchesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//button[.//span[contains(text(),'Refresh')]]")
    private WebElement refreshButton;

    @FindBy(xpath = "//span[contains(text(),'Add filter')]/ancestor::button")
    private WebElement addFilterButton;

    private final By filterInputLocator = By.xpath("//input[@placeholder='Enter name']");
    private final By launchItemsLocator = By.cssSelector(".gridRow__grid-row-wrapper--xj8DG");
    private final By firstLaunchLink = By.cssSelector("a.itemInfo__name-link--oIaqj");
    private final By launchDetailTitle = By.cssSelector("div.itemInfo__name--Nz97v > span");
    private final By launchRow = By.cssSelector(".gridRow__grid-row-wrapper--xj8DG");
    private final By launchLink = By.cssSelector("a.itemInfo__name-link--oIaqj");

    public LaunchesPage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void waitForLaunchTable() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(launchItemsLocator));
    }

    public void waitForLaunchTableUpdated(int previousCount) {
        wait.until(driver -> {
            int newCount = driver.findElements(launchItemsLocator).size();
            return newCount != previousCount;
        });
    }

    public void waitForFilterInputVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterInputLocator));
    }

    public void waitForLaunchItemsMoreThan(int n) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(launchItemsLocator, n));
    }

    public int getLaunchesCount() {
        return driver.findElements(launchItemsLocator).size();
    }

    public void clickRefresh() {
        wait.until(ExpectedConditions.elementToBeClickable(refreshButton)).click();
    }

    public void clickAddFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(addFilterButton)).click();
    }

    public void enterFilterText(String text) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(filterInputLocator));
        input.clear();
        input.sendKeys(text);
    }

    public void openFirstLaunch() {
        waitForLaunchTable();

        WebElement first = driver.findElements(launchItemsLocator).get(0);
        WebElement link = first.findElement(firstLaunchLink);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

        wait.until(ExpectedConditions.visibilityOfElementLocated(launchDetailTitle));
    }

    public String getFirstLaunchId() {
        WebElement firstRow = driver.findElements(launchRow).get(0);
        String href = firstRow.findElement(launchLink).getAttribute("href");
        return href.substring(href.lastIndexOf("/") + 1);
    }

}