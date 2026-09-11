package com.payguard.tests;

import com.payguard.utils.DBUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayGuardDBTest {

    @BeforeClass
    public void setupDatabase() {
        DBUtil.initDatabase();
        DBUtil.insertTransaction("TXN_10928", "neelam.qa@fintech.com", 2499.50, "SETTLED");
    }

    @Test(priority = 1, description = "Validate Ledger Transaction Persistence & State via JDBC")
    public void testTransactionPersistence() throws SQLException {
        String query = "SELECT user_email, amount, status FROM transactions WHERE txn_id = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "TXN_10928");
            ResultSet rs = pstmt.executeQuery();

            Assert.assertTrue(rs.next(), "Transaction record not found in ledger database.");
            Assert.assertEquals(rs.getString("user_email"), "neelam.qa@fintech.com");
            Assert.assertEquals(rs.getDouble("amount"), 2499.50);
            Assert.assertEquals(rs.getString("status"), "SETTLED");
        }
    }
}
