package framework.tests.api;

import framework.core.model.LaunchDto;
import framework.core.model.LaunchResponse;
import framework.core.model.request.ClusterRequest;
import framework.core.model.request.LaunchInfoRequest;
import framework.core.utils.LocalJsonMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class LaunchesApiUtils {
    private final RequestSpecification req;
    private final String project;

    public LaunchesApiUtils(RequestSpecification req, String project) {
        this.req = req;
        this.project = project;
    }

    @Step("Получение списка запусков")
    public List<LaunchDto> getLaunches() {
        Response resp = req.get("/" + project + "/launch")
                .then().statusCode(200)
                .extract().response();

        LaunchResponse result = LocalJsonMapper.fromJson(resp.asString(), LaunchResponse.class);
        return result.getContent();
    }

    @Step("Получение запуска по UUID: {uuid}")
    public LaunchDto getLaunchByUuid(String uuid) {
        Response resp = req.get("/" + project + "/launch/uuid/" + uuid)
                .then().statusCode(200)
                .extract().response();

        return LocalJsonMapper.fromJson(resp.asString(), LaunchDto.class);
    }

    @Step("Создание кластера для launchId={launchId}")
    public String createCluster(int launchId) {
        ClusterRequest r = new ClusterRequest();
        r.setLaunchId(launchId);
        r.setRemoveNumbers(true);

        Response resp = req.body(r)
                .post("/" + project + "/launch/cluster")
                .then().statusCode(200)
                .extract().response();

        return resp.asString();
    }

    @Step("Обновление данных запуска")
    public String updateLaunchInfo(LaunchInfoRequest data) {
        Response resp = req.body(data)
                .put("/" + project + "/launch/info")
                .then().statusCode(200)
                .extract().response();

        return resp.asString();
    }

    @Step("Получение отчёта для launchId={launchId}")
    public String getReport(int launchId) {
        Response resp = req.get("/" + project + "/launch/" + launchId + "/report")
                .then().statusCode(200)
                .extract().response();

        return resp.asString();
    }

    @Step("Удаление запуска launchId={launchId}")
    public String deleteLaunch(int launchId) {
        Response resp = req.delete("/" + project + "/launch/" + launchId)
                .then().statusCode(200)
                .extract().response();

        return resp.asString();
    }
}
