package com.payguard.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PayGuardAPITest {

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(priority = 1, description = "Verify Transaction Gateway API Response Code and Latency")
    public void testPaymentGatewayStatus() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .time(lessThan(8000L));
    }

    @Test(priority = 2, description = "Verify Token Creation Payload Contract")
    public void testSecurePaymentTokenCreation() {
        String requestBody = "{\"title\": \"Neelam Payment Settlement\", \"body\": \"Ledger Verified\", \"userId\": 101}";

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("title", equalTo("Neelam Payment Settlement"))
            .body("id", notNullValue());
    }
}
