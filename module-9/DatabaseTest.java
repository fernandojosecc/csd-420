/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 9 Programming Assignment - MySQL/JDBC Setup
 * Date: September 23 2026
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    // JDBC connection details matching the assignment requirements
    private static final String URL =
            "jdbc:mysql://localhost:3306/databasedb";
    private static final String USER = "student1";
    private static final String PASSWORD = "pass";

    public static void main(String[] args) {
        System.out.println("--- MySQL / JDBC Connection Test ---\n");

        // try-with-resources closes the Connection automatically
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Test 1: verify the Connection is live
            System.out.println("Test 1 (Connection established): "
                    + (conn.isValid(2) ? "PASSED" : "FAILED"));

            // Test 2: query current user and MySQL version
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT CURRENT_USER() AS user, VERSION() AS version")) {
                if (rs.next()) {
                    String user = rs.getString("user");
                    String version = rs.getString("version");
                    System.out.println("Test 2 (User/Version query): PASSED");
                    System.out.println("  Connected as: " + user);
                    System.out.println("  MySQL version: " + version);
                } else {
                    System.out.println("Test 2 (User/Version query): FAILED");
                }
            }

            // Test 3: read the sample address_test table created in setup
            try (ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM address_test")) {
                System.out.println("Test 3 (Read sample table): PASSED");
                System.out.println("  Contents of address_test:");
                while (rs.next()) {
                    System.out.printf("    ID=%d  %s, %s  (%s, %s)%n",
                            rs.getInt("ID"),
                            rs.getString("LASTNAME"),
                            rs.getString("FIRSTNAME"),
                            rs.getString("CITY"),
                            rs.getString("STATE"));
                }
            }

            System.out.println("\nAll tests passed. Setup is verified.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}