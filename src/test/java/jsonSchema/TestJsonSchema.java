package jsonSchema;

import config.EnvConfig;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class TestJsonSchema {
    @Test
    public void testJson() {
        String requestBody = "{ \"name\": \"Baginda\", \"job\": \"QA\" }";
        given()
                .baseUri(EnvConfig.get("base.uri"))
                .header(EnvConfig.get("api.key"), EnvConfig.get("api.value"))
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post("/api/users")
            .then()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schema/user-schema.json"));
    }
}