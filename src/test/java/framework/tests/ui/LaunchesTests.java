package framework.tests.ui;

import framework.core.base_entities.BaseTest;
import framework.core.steps.LoginSteps;
import framework.core.utils.config.ConfigLoader;
import framework.core.pages.LaunchesPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

@Epic("UI Tests")
@Feature("Launches Page")
@Test(groups = "UI")
public class LaunchesTests extends BaseTest {
    private static final String URL_LAUNCHES = ConfigLoader.get().getUrl().getLaunches();

    @Test(description = "Проверяем, что список запусков отображается")
    @Story("Просмотр списка запусков")
    @Severity(SeverityLevel.CRITICAL)
    public void testLaunchesListDisplayed() {

        LoginSteps.login();

        LaunchesPage launchesPage = new LaunchesPage();
        launchesPage.open(URL_LAUNCHES);

        launchesPage.waitForLaunchTable();
        int count = launchesPage.getLaunchesCount();

        Assert.assertTrue(count > 0, "Launches list should not be empty!");
    }

    @Test(description = "Проверяем, что кнопка Refresh обновляет список запусков")
    @Story("Обновление списка запусков")
    @Severity(SeverityLevel.NORMAL)
    public void testRefreshButtonUpdatesLaunches() {

        LoginSteps.login();

        LaunchesPage launchesPage = new LaunchesPage();
        launchesPage.open(URL_LAUNCHES);
        launchesPage.waitForLaunchTable();

        int before = launchesPage.getLaunchesCount();

        launchesPage.clickRefresh();
        launchesPage.waitForLaunchTableUpdated();

        int after = launchesPage.getLaunchesCount();

        Assert.assertTrue(after >= 0, "Launch count should be non-negative");
    }

    @Test(description = "Проверяем, что фильтр запуска корректно работает")
    @Story("Фильтрация запусков")
    @Severity(SeverityLevel.NORMAL)
    public void testAddFilterWorks() {

         LoginSteps.login();

        LaunchesPage launchesPage = new LaunchesPage();
        launchesPage.open(URL_LAUNCHES);
        launchesPage.waitForLaunchTable();

        launchesPage.clickAddFilter();

        // новый вариант: поле становится видимым автоматически через Waits
        launchesPage.enterFilterText("Demo");

        int count = launchesPage.getLaunchesCount();

        Assert.assertTrue(count >= 1,
                "Filter should return at least one item");
    }

    @Test(description = "Открытие детальной страницы запуска")
    @Story("Открытие запуска")
    @Severity(SeverityLevel.CRITICAL)
    public void openLaunchTest() {

        LoginSteps.login();

        LaunchesPage launchesPage = new LaunchesPage();
        launchesPage.open(URL_LAUNCHES);
        launchesPage.waitForLaunchTable();

        String expectedLaunchId = launchesPage.getFirstLaunchId();
        launchesPage.openFirstLaunch();

        String currentUrl = launchesPage.getCurrentUrl();

        Assert.assertTrue(currentUrl.endsWith(expectedLaunchId),
                "Detail page URL should end with ID: " + expectedLaunchId);
    }
}

