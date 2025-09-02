package test;

import config.EnvConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class TestPostUser {
    String apiKeyHeader;
    String apiKeyValue;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = EnvConfig.get("base.uri");
        apiKeyHeader = EnvConfig.get("api.key");
        apiKeyValue = EnvConfig.get("api.value");
    }

    @Test
    @Feature("Post User")
    @Description("Verify success create user")
    public void testCreateUser() {
        given()
                .baseUri(EnvConfig.get("base.uri"))
                .header(apiKeyHeader, apiKeyValue)
                .contentType("application/json")
                .body("{\"name\": \"Baginda\", \"job\": \"QA\"}").
            when()
                .post("/api/users").
            then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/user-schema.json"));
    }

    @Test
    public void testSimplePostWithoutGiven() {
        String requestBody = "{ \"name\": \"Baginda\", \"job\": \"Raja QA\" }";
        Response response = RestAssured
                .with()
                .header(apiKeyHeader,apiKeyValue)
                .contentType("application/json")
                .body(requestBody)
                .request("POST", "/api/users");
        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.jsonPath().getString("name"), "Baginda");
        assertEquals(response.jsonPath().getString("job"), "Raja QA");
        assertNotNull(response.jsonPath().getString("id"));
        assertNotNull(response.jsonPath().getString("createdAt"));
        MatcherAssert.assertThat(
                response.getBody().asString(),
                matchesJsonSchemaInClasspath("schema/user-schema.json")
        );
    }
}