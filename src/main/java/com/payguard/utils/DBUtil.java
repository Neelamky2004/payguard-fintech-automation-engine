package com.payguard.utils;

import java.sql.*;

public class DBUtil {
    private static final String URL = "jdbc:h2:mem:payguarddb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void initDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                                "txn_id VARCHAR(50) PRIMARY KEY, " +
                                "user_email VARCHAR(100), " +
                                "amount DOUBLE, " +
                                "status VARCHAR(20));";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertTransaction(String txnId, String email, double amount, String status) {
        String insertSQL = "INSERT INTO transactions (txn_id, user_email, amount, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, txnId);
            pstmt.setString(2, email);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
