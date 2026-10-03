/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 10 Programming Assignment - JDBC Fan Manager
 * Date: September 2026
 *
 * Description:
 * JavaFX application that connects to the databasedb MySQL database
 * as user student1 and provides a simple interface for viewing and
 * updating rows in the "fans" table. The table has four columns:
 *   ID (integer, primary key)
 *   firstname (varchar 25)
 *   lastname (varchar 25)
 *   favoriteteam (varchar 25)
 *
 * The interface has two buttons:
 *   Display - looks up the record whose ID matches the ID field and
 *             fills the firstname, lastname, and favoriteteam fields
 *             with the values from the database.
 *   Update  - takes the current values in all four fields and applies
 *             them to the matching row in the database using a SQL
 *             UPDATE statement.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FanManager extends Application {

    // JDBC connection details matching the assignment requirements
    private static final String URL =
            "jdbc:mysql://localhost:3306/databasedb";
    private static final String USER = "student1";
    private static final String PASSWORD = "pass";

    // Input fields shared between the Display and Update handlers
    private final TextField idField = new TextField();
    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField favoriteTeamField = new TextField();

    // Status message shown below the buttons after each action
    private final Label statusLabel = new Label(" ");

    @Override
    public void start(Stage primaryStage) {
        // Form layout: labels on the left, text fields on the right
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20));

        form.add(new Label("ID:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("First Name:"), 0, 1);
        form.add(firstNameField, 1, 1);
        form.add(new Label("Last Name:"), 0, 2);
        form.add(lastNameField, 1, 2);
        form.add(new Label("Favorite Team:"), 0, 3);
        form.add(favoriteTeamField, 1, 3);

        // Buttons wired to their handlers using lambdas
        Button displayButton = new Button("Display");
        Button updateButton = new Button("Update");
        displayButton.setOnAction(e -> displayRecord());
        updateButton.setOnAction(e -> updateRecord());

        HBox buttonRow = new HBox(15, displayButton, updateButton);
        buttonRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, form, buttonRow, statusLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Fan Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Run the built-in tests once the UI is up so results print
        // to the console for verification
        runTests();
    }

    /**
     * Looks up the record whose ID matches the ID field and fills the
     * other fields with the values from the database.
     */
    private void displayRecord() {
        int id;
        try {
            id = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("ID must be a number.");
            return;
        }

        String sql = "SELECT firstname, lastname, favoriteteam "
                + "FROM fans WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    firstNameField.setText(rs.getString("firstname"));
                    lastNameField.setText(rs.getString("lastname"));
                    favoriteTeamField.setText(rs.getString("favoriteteam"));
                    statusLabel.setText("Record displayed for ID " + id + ".");
                } else {
                    // Clear the display fields when no match is found
                    firstNameField.clear();
                    lastNameField.clear();
                    favoriteTeamField.clear();
                    statusLabel.setText("No record found for ID " + id + ".");
                }
            }
        } catch (SQLException e) {
            statusLabel.setText("Database error: " + e.getMessage());
        }
    }

    /**
     * Applies the current values in all four fields to the matching
     * row in the database. Uses a parameterized UPDATE statement so
     * user input is safely escaped.
     */
    private void updateRecord() {
        int id;
        try {
            id = Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("ID must be a number.");
            return;
        }

        String sql = "UPDATE fans SET firstname = ?, lastname = ?, "
                + "favoriteteam = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstNameField.getText().trim());
            stmt.setString(2, lastNameField.getText().trim());
            stmt.setString(3, favoriteTeamField.getText().trim());
            stmt.setInt(4, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                statusLabel.setText("Record updated for ID " + id + ".");
            } else {
                statusLabel.setText("No record found for ID " + id + ".");
            }
        } catch (SQLException e) {
            statusLabel.setText("Database error: " + e.getMessage());
        }
    }

    /**
     * Basic startup tests that verify a Connection can be established
     * and that the fans table is reachable. Results print to the
     * console so they show up alongside compile output.
     */
    private void runTests() {
        System.out.println("--- Running startup tests ---");

        // Test 1: connection can be established with the required credentials
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Test 1 (Connection to databasedb): "
                    + (conn.isValid(2) ? "PASSED" : "FAILED"));

            // Test 2: fans table exists and can be counted
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) AS n FROM fans");
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Test 2 (fans table reachable): PASSED");
                    System.out.println("  Row count: " + rs.getInt("n"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Startup tests FAILED: " + e.getMessage());
        }

        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}