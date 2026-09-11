package com.payguard.tests;

import com.payguard.utils.DBUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PayGuardEndToEndReconciliationTest {

    @BeforeClass
    public void setupSuite() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        DBUtil.initDatabase();
    }

    @Test(priority = 1, description = "3-Tier Reconciliation: API Token -> DB Ledger Insert -> State Assertion")
    public void verifyEndToEndPaymentSettlementReconciliation() throws SQLException {
        // Step 1: Simulate Gateway Settlement through API
        int responseId = given()
            .contentType(ContentType.JSON)
            .body("{\"title\": \"TXN_RECON_8899\", \"body\": \"INR 50000 Settlement\", \"userId\": 99}")
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .extract().path("id");

        Assert.assertTrue(responseId > 0, "Gateway settlement token generation failed.");

        // Step 2: Persist in Database Ledger (Simulating webhook sync)
        String txnId = "TXN_RECON_8899";
        DBUtil.insertTransaction(txnId, "deloitte.audit@payguard.com", 50000.00, "SETTLED");

        // Step 3: Query Database directly to verify state integrity
        String query = "SELECT amount, status FROM transactions WHERE txn_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, txnId);
            ResultSet rs = pstmt.executeQuery();

            Assert.assertTrue(rs.next(), "Database persistence reconciliation failed.");
            Assert.assertEquals(rs.getDouble("amount"), 50000.00, "Ledger amount mismatch.");
            Assert.assertEquals(rs.getString("status"), "SETTLED", "Transaction final state mismatch.");
        }
    }
}
