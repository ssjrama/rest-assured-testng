package test;

import config.EnvConfig;
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

public class TestGetUser {
    String apiKeyHeader;
    String apiKeyValue;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = EnvConfig.get("base.uri");
        apiKeyHeader = EnvConfig.get("api.key");
        apiKeyValue = EnvConfig.get("api.value");
    }

    @Test
    public void testGetUsers() {
        given().
            when()
                .header(apiKeyHeader,apiKeyValue);
                get("/api/users/2").
            then().
                assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("schema/get-user-schema.json"));
    }

    @Test
    public void getUser() {
        Response response = RestAssured
                .with()
                .header(apiKeyHeader,apiKeyValue)
                .get("/api/users/2");
        assertEquals(response.getStatusCode(), 200);
        MatcherAssert.assertThat(
                response.getBody().asString(),
                matchesJsonSchemaInClasspath("schema/get-user-schema.json")
        );
    }
}