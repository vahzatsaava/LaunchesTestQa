package framework.tests.api;

import framework.core.base_entities.BaseApiTest;
import framework.core.model.LaunchDto;
import framework.core.steps.LaunchesApiSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

@Epic("API Tests")
@Feature("Launches API")
@Test(groups = "API")
public class LaunchesApiTest extends BaseApiTest {

    private LaunchesApiSteps api;

    @BeforeClass
    public void initApi() {
        api = new LaunchesApiSteps(request(), PROJECT_NAME);
    }

    @Test(description = "Получение списка запусков")
    @Severity(SeverityLevel.CRITICAL)
    public void getLaunchesTest() {
        List<LaunchDto> launches = api.getLaunches();
        Assert.assertFalse(launches.isEmpty(), "Launch list must not be empty");
    }

    @Test(description = "Получение запуска по UUID")
    public void getLaunchByIdTest() {
        List<LaunchDto> launches = api.getLaunches();
        LaunchDto first = launches.get(0);

        LaunchDto dto = api.getLaunchByUuid(first.getUuid());

        Assert.assertEquals(dto.getUuid(), first.getUuid());
    }

    @Test(description = "Создание кластера")
    public void createClusterTest() {
        int id = api.getLaunches().get(0).getId();
        String result = api.createCluster(id);

        Assert.assertTrue(result.contains("generation"), "Cluster was not created");
    }

    @Test(description = "Обновление данных запуска")
    public void updateLaunchInfoTest() {
        int id = api.getLaunches().get(0).getId();
        var req = LaunchInfoRequestBuilder.build(id);

        String result = api.updateLaunchInfo(req);

        Assert.assertTrue(result.contains("success") || result.contains("updated"));
    }

    @Test(description = "Получение отчёта запуска")
    public void getLaunchReportTest() {
        int id = api.getLaunches().get(0).getId();
        String report = api.getReport(id);
        Assert.assertNotNull(report);
    }

    @Test(description = "Удаление запуска")
    public void deleteLaunch() {
        int id = api.getLaunches().get(0).getId();
        String result = api.deleteLaunch(id);
        Assert.assertNotNull(result);
    }
}
