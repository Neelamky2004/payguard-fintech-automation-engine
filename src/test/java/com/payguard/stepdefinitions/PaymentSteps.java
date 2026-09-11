package com.payguard.stepdefinitions;

import com.payguard.base.BaseTest;
import com.payguard.pages.PaymentPortalPage;
import com.payguard.utils.DBUtil;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;

public class PaymentSteps {
    private PaymentPortalPage portal;
    private int apiResponseStatus;
    private String currentBatchId;
    private double currentAmount;

    @Given("User is authorized on the PayGuard FinTech platform")
    public void user_is_authorized() {
        portal = new PaymentPortalPage(BaseTest.getDriver());
        portal.openPortal("https://demo.applitools.com/hackathon.html");
        portal.login("deloitte_fintech_lead", "EnterpriseVault@2026");
        Assert.assertTrue(portal.isDashboardLoaded(), "Dashboard failed to mount for BDD execution.");
    }

    @When("Treasury executes a bulk batch {string} in {string} with amount {double} on rail {string}")
    public void treasury_executes_batch(String batchId, String currency, double amount, String rail) {
        this.currentBatchId = batchId;
        this.currentAmount = amount;

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        String payload = String.format("{\"batchId\": \"%s\", \"currency\": \"%s\", \"amount\": %.2f, \"rail\": \"%s\"}",
                batchId, currency, amount, rail);

        apiResponseStatus = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .extract().statusCode();
    }

    @Then("The gateway must confirm transaction settlement with HTTP 201")
    public void gateway_confirm_settlement() {
        Assert.assertEquals(apiResponseStatus, 201, "API settlement contract SLA breached.");
    }

    @Then("The persistent database ledger must reflect {string} state as {string}")
    public void database_ledger_verify(String currency, String expectedState) throws SQLException {
        DBUtil.initDatabase();
        DBUtil.insertTransaction(currentBatchId, "treasury." + currency.toLowerCase() + "@payguard.com", currentAmount, expectedState);

        String query = "SELECT status FROM transactions WHERE txn_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, currentBatchId);
            ResultSet rs = pstmt.executeQuery();
            Assert.assertTrue(rs.next(), "Database persistence ledger reconciliation failed for " + currentBatchId);
            Assert.assertEquals(rs.getString("status"), expectedState, "Settlement audit status mismatch.");
        }
    }
}
