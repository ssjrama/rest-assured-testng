package test;

import config.EnvConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.testng.Assert.assertEquals;

public class TestDeleteUser {
    String apiKeyHeader;
    String apiKeyValue;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = EnvConfig.get("base.uri");
        apiKeyHeader = EnvConfig.get("api.key");
        apiKeyValue = EnvConfig.get("api.value");
    }

    @Test
    public void deleteUser() {
        Response response = RestAssured
                .with()
                .header(apiKeyHeader,apiKeyValue)
                .delete("/api/users/2");
        assertEquals(response.getStatusCode(), 204);
    }
}