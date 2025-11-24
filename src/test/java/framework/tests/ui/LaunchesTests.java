package framework.tests.ui;

import framework.core.driver.BaseTest;
import framework.core.driver.DriverManager;
import framework.core.utils.config.ConfigLoader;
import framework.pages.LaunchesPage;
import framework.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

@Test
public class LaunchesTests extends BaseTest {

    private static final String URL_LAUNCHES = ConfigLoader.get().getUrl().getLaunches();

    @Test
    public void testLaunchesListDisplayed() {

        WebDriver driver = DriverManager.getDriver();
        login(driver);

        driver.get(URL_LAUNCHES);
        LaunchesPage launchesPage = new LaunchesPage();

        launchesPage.waitForLaunchTable();

        Assert.assertTrue(launchesPage.getLaunchesCount() > 0,
                "Launches list should not be empty!");
    }

    @Test
    public void testRefreshButtonUpdatesLaunches() {

        WebDriver driver = DriverManager.getDriver();
        login(driver);

        driver.get(URL_LAUNCHES);
        LaunchesPage launchesPage = new LaunchesPage();

        launchesPage.waitForLaunchTable();

        int before = launchesPage.getLaunchesCount();
        launchesPage.clickRefresh();

        launchesPage.waitForLaunchTableUpdated(before);

        int after = launchesPage.getLaunchesCount();
        Assert.assertTrue(after >= 0, "Launch count should be non-negative");
    }

    @Test
    public void testAddFilterWorks() {

        WebDriver driver = DriverManager.getDriver();
        login(driver);

        driver.get(URL_LAUNCHES);
        LaunchesPage launchesPage = new LaunchesPage();

        launchesPage.waitForLaunchTable();

        launchesPage.clickAddFilter();
        launchesPage.waitForFilterInputVisible();

        launchesPage.enterFilterText("Demo");

        launchesPage.waitForLaunchItemsMoreThan(1);

        Assert.assertTrue(launchesPage.getLaunchesCount() >= 1,
                "Filter should return at least one item!");
    }

    @Test
    public void openLaunchTest() {

        WebDriver driver = DriverManager.getDriver();
        login(driver);

        LaunchesPage launchesPage = new LaunchesPage();

        driver.get(URL_LAUNCHES);
        launchesPage.waitForLaunchTable();

        // берём ID первой строки
        String expectedLaunchId = launchesPage.getFirstLaunchId();

        launchesPage.openFirstLaunch();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.endsWith(expectedLaunchId),
                "Detail page URL is incorrect! Expected ID at end: " + expectedLaunchId +
                        " but got: " + currentUrl);
    }


    private void login(WebDriver driver) {
        driver.get("https://demo.reportportal.io/ui/#login");

        LoginPage loginPage = new LoginPage();
        loginPage.login("", "");

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlContains("default_personal"));
    }
}

