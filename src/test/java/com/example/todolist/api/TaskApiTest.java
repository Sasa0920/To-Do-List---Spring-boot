package com.example.todolist.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskApiTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void createTask_shouldReturnTaskAnd200() {
        String payload = """
            {"title":"API Created Task","completed":false}
            """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/tasks")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("title", equalTo("API Created Task"))
                .body("completed", equalTo(false));
    }

    @Test
    void getTasks_shouldContainCreatedTask() {

        String title = "GetTasksCheck-" + System.currentTimeMillis();
        String payload = "{\"title\":\"" + title + "\",\"completed\":false}";


        given().contentType(ContentType.JSON).body(payload).when().post("/api/tasks").then().statusCode(anyOf(is(200), is(201)));


        when().get("/api/tasks")
                .then()
                .statusCode(200)
                .body("title", hasItem(title));
    }
}

