package test;

import config.EnvConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestLogin {
    @Test
    @Feature("Login")
    @Description("Login Success")
    public void loginSuccess() {
        given()
                .baseUri(EnvConfig.get("base.security"))
                .contentType("application/json")
                .body("{\"userName\": \"superuser01\", \"password\": \"superuser_password\"}").
            when()
                .post("/api/auth/login").
            then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/login.json"));
    }

    @Test
    @Feature("Login")
    @Description("Login Failed")
    public void loginFailed() {
        given()
                .baseUri(EnvConfig.get("base.security"))
                .contentType("application/json")
                .body("{\"userName\": \"superuser01\", \"password\": \"superuser_passwor\"}").
                when()
                .post("/api/auth/login").
                then()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("schema/login-failed.json"));
    }
}