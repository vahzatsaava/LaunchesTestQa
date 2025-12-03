package framework.core.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LaunchesPage extends BasePage{

    private final By launchRows = By.xpath("//div[contains(@class,'gridRow')]");
    private final By filterInput = By.xpath("//input[@placeholder='Enter name']");
    private final By firstLaunchLink = By.xpath(".//a[contains(@class,'name')]");


    public void waitForLaunchTable() {
        waitForVisible(launchRows);
    }

    public void waitForLaunchTableUpdated(int previousCount) {
        wait.until(driver -> driver.findElements(launchRows).size() != previousCount);
    }

    public int getLaunchesCount() {
        return driver.findElements(launchRows).size();
    }

    public void clickAddFilter() {
        clickButtonByText("Add filter");
    }

    public void waitForFilterInputVisible() {
        waitForVisible(filterInput);
    }

    public void enterFilterText(String text) {
        type(filterInput, text);
    }

    public void clickRefresh() {
        clickButtonByText("Refresh");
    }

    public void openFirstLaunch() {
        WebElement first = driver.findElements(launchRows).get(0);
        WebElement link = first.findElement(firstLaunchLink);
        jsClick(link);
    }

    public String getFirstLaunchId() {
        WebElement first = driver.findElements(launchRows).get(0);
        WebElement link = first.findElement(firstLaunchLink);

        String href = link.getAttribute("href");
        return href.substring(href.lastIndexOf("/") + 1);
    }


}
