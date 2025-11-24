package framework.tests.api;

import framework.core.utils.LocalJsonMapper;
import framework.core.utils.config.ConfigLoader;
import framework.model.LaunchDto;
import framework.model.LaunchResponse;
import framework.model.request.ClusterRequest;
import framework.model.request.LaunchInfoRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@Test
public class LaunchesApiTest {
    private static final String API_KEY = ConfigLoader.get().getApi().getKey();
    private static final String BASE_URL = ConfigLoader.get().getBase().getUrl();
    private static final String PROJECT_NAME = ConfigLoader.get().getBase().getProjectName();


    private RequestSpecification createRequestSpec() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .contentType(ContentType.JSON);
    }

    @Test
    public void getLaunchesTest() {
        Response response = createRequestSpec()
                .get("/" + PROJECT_NAME + "/launch")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String json = response.getBody().asString();
        LaunchResponse launchResponse = LocalJsonMapper.fromJson(json, LaunchResponse.class);
        List<LaunchDto> launches = launchResponse.getContent();

        launches.forEach(l -> System.out.println(l.getName() + " : " + l.getStatus() + " : " + l.getUuid()));
    }

    @Test
    public void getLaunchByIdTest() {
        String launchUUID = getFirstLaunchFromList().getUuid();
        Response response = createRequestSpec()
                .get("/" + PROJECT_NAME + "/launch/uuid/" + launchUUID)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String rawJson = response.getBody().asString();
        LaunchDto launch = LocalJsonMapper.fromJson(rawJson, LaunchDto.class);

        log.info("Launch name - {}", LocalJsonMapper.prettyJson(rawJson));
        assertEquals("Demo Api Tests", launch.getName());
    }

    @Test
    public void createClusterTest() {
        int launchId = getFirstLaunchFromList().getId();

        ClusterRequest request = new ClusterRequest();
        request.setLaunchId(launchId);
        request.setRemoveNumbers(true);

        Response response = createRequestSpec()
                .body(request)
                .post("/" + PROJECT_NAME + "/launch/cluster")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String rawJson = response.getBody().asString();
        log.info("Response: {}", rawJson);

        assertTrue(rawJson.contains("Clusters generation for launch with ID"),
                "Response should confirm cluster generation started");
    }


    @Test
    public void updateLaunchInfoTest() {
        LaunchInfoRequest request = getRaunchInfoRequest();

        Response response = createRequestSpec()
                .body(request)
                .put("/" + PROJECT_NAME + "/launch/info")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String rawJson = response.getBody().asString();
        log.info("Response: {}", rawJson);

        assertTrue(rawJson.contains("Attributes successfully updated") || rawJson.contains("success"),
                "Response should confirm attributes were updated");
    }

    @Test
    public void getLaunchReportTest() {
        int launchId = getFirstLaunchFromList().getId();

        Response response = createRequestSpec()
                .get("/" + PROJECT_NAME + "/launch/" + launchId + "/report")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String reportJson = response.getBody().asString();
        log.info("Report: {}", reportJson);
        assertNotNull(reportJson);
    }

    @Test
    public void deleteLaunch() {

        int launchId = getFirstLaunchFromList().getId();

        Response response = createRequestSpec()
                .delete("/" + PROJECT_NAME + "/launch/" + launchId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        String reportJson = response.getBody().asString();
        log.info("Report: {}", reportJson);
    }


    private LaunchDto getFirstLaunchFromList(){
        Response responseList = createRequestSpec()
                .get("/" + PROJECT_NAME + "/launch")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String json = responseList.getBody().asString();
        LaunchResponse launchResponse = LocalJsonMapper.fromJson(json, LaunchResponse.class);
        List<LaunchDto> launches = launchResponse.getContent();

        return launches.get(launches.size() - 1);
    }

    private LaunchInfoRequest getRaunchInfoRequest() {
        int launchId = getFirstLaunchFromList().getId();

        LaunchInfoRequest request = new LaunchInfoRequest();

        request.setIds(Collections.singletonList(launchId));

        LaunchInfoRequest.Description desc = new LaunchInfoRequest.Description();
        desc.setComment("DDDDD");
        desc.setAction("CREATE");
        request.setDescription(desc);

        LaunchInfoRequest.KeyValue from = new LaunchInfoRequest.KeyValue();
        from.setKey("DDDD");
        from.setValue("DDDD");

        LaunchInfoRequest.KeyValue to = new LaunchInfoRequest.KeyValue();
        to.setKey("DDDD");
        to.setValue("DDDD");

        LaunchInfoRequest.AttributeChange attr = new LaunchInfoRequest.AttributeChange();
        attr.setFrom(from);
        attr.setTo(to);
        attr.setAction("CREATE");

        request.setAttributes(Collections.singletonList(attr));

        return request;
    }

}
