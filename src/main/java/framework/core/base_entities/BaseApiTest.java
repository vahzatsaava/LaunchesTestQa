package framework.core.base_entities;

import framework.core.utils.config.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public abstract class BaseApiTest {

    protected String API_KEY;
    protected String BASE_URL;
    protected String PROJECT_NAME;

    @BeforeClass
    public void setUpApi() {
        API_KEY = ConfigLoader.get().getApi().getKey();
        BASE_URL = ConfigLoader.get().getBase().getUrl();
        PROJECT_NAME = ConfigLoader.get().getBase().getProjectName();

        RestAssured.baseURI = BASE_URL;
    }

    protected RequestSpecification request() {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + API_KEY)
                .contentType("application/json");
    }
}
