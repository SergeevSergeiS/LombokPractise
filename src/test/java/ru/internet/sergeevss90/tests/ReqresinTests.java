package ru.internet.sergeevss90.tests;

import org.junit.jupiter.api.Test;
import ru.internet.sergeevss90.models.RequestBuilder;
import ru.internet.sergeevss90.models.UserData;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.internet.sergeevss90.specs.Specs.*;
import static org.hamcrest.Matchers.is;

public class ReqresinTests {
    @Test
    void resourceNotFoundTest() {
        given()
                .spec(request)
                .when()
                .get("/unknown/23")
                .then()
                .spec(response404);
    }

    @Test
    void missingPasswordRegisterTest() {
        String body = "{ \"email\": \"sydney@fife\" }";
        given()
                .spec(request)
                .body(body)
                .when()
                .post("/register")
                .then()
                .spec(response400);
    }

    @Test
    void deleteUser() {
        given()
                .spec(request)
                .when()
                .delete("/users/2")
                .then()
                .spec(response204);
    }

    @Test
    void singleTest() {
        given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    @Test
    public void checkEmailUsingGroovy() {
        given()
                .spec(request)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()[1]",
                        is("janet.weaver@reqres.in"));
    }

    @Test
    void createNewUser() {
        String name = "morpheus";
        String job = "leader";
        RequestBuilder requestCreate = new RequestBuilder();
        requestCreate.setJob("leader");
        requestCreate.setName("morpheus");
        UserData data = given()
                .spec(request)
                .body(requestCreate)
                .when()
                .post("/users")
                .then()
                .spec(response201)
                .extract().as(UserData.class);
        assertEquals(name, data.getName());
        assertEquals(job, data.getJob());
    }
}