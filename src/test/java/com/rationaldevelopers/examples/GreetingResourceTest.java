package com.rationaldevelopers.examples;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

  @Test
  public void testEndPoint() {
    given()
        .when().get("/greeting")
        .then()
        .statusCode(200)
        .body(is("Yo!"));
  }


  @Test
  public void testEndPoint2() {
    final String uuid = UUID.randomUUID().toString();
    given()
        .when().get("/greeting/" + uuid)
        .then()
        .statusCode(200)
        .body(is("hello: " + uuid));
  }


}
