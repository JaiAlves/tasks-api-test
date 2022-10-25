package com.devops.tasks.api.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class APITest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI ="http://localhost:8001/tasks-backend";
    }

    @Test
    public void test() {
        RestAssured.given()
                .when()
                    .get("/todo/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void mustAddTaskSuccess() {
        RestAssured.given()
                .body("{\n" +
                        "        \"task\": \"ajuste no carro\",\n" +
                        "        \"dueDate\": \"2022-10-26\"\n" +
                        "    }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201);
    }

    @Test
    public void doNotMustAddInvalidTask() {
        RestAssured.given()
                .body("{\n" +
                        "        \"task\": \"ajuste no carro\",\n" +
                        "        \"dueDate\": \"2022-10-20\"\n" +
                        "    }")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }

}
