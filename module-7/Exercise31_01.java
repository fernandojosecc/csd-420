/**
 * File: Exercise31_01.java
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 7 Programming Assignment - JavaFX CSS
 * Date: September 15th 2026
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Exercise31_01 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Four circles: two styled by class, two styled by ID
        Circle circle1 = new Circle(25);
        circle1.getStyleClass().add("plainStyle");           // class selector

        Circle circle2 = new Circle(25);
        circle2.getStyleClass().add("plainStyle");           // same class, reusable

        Circle circle3 = new Circle(25);
        circle3.setId("redStyle");                           // ID selector

        Circle circle4 = new Circle(25);
        circle4.setId("greenStyle");                         // ID selector

        // Horizontal container that lays all four circles side by side
        HBox root = new HBox(15, circle1, circle2, circle3, circle4);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Build the scene and attach the external stylesheet
        Scene scene = new Scene(root, 320, 150);
        scene.getStylesheets().add(
                getClass().getResource("mystyle.css").toExternalForm()
        );

        primaryStage.setTitle("Exercise31_01");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Simple runtime verification that the styles were applied correctly
        runTests(circle1, circle2, circle3, circle4);
    }

    /**
     * Verifies each circle has the expected style class or ID assigned.
     * If the CSS file loaded correctly, the visual output should match
     * the assignment reference image.
     */
    private void runTests(Circle c1, Circle c2, Circle c3, Circle c4) {
        System.out.println("--- Running tests ---");

        boolean t1 = c1.getStyleClass().contains("plainStyle");
        System.out.println("Test 1 (circle1 uses .plainStyle class): "
                + (t1 ? "PASSED" : "FAILED"));

        boolean t2 = c2.getStyleClass().contains("plainStyle");
        System.out.println("Test 2 (circle2 uses .plainStyle class): "
                + (t2 ? "PASSED" : "FAILED"));

        boolean t3 = "redStyle".equals(c3.getId());
        System.out.println("Test 3 (circle3 has #redStyle id): "
                + (t3 ? "PASSED" : "FAILED"));

        boolean t4 = "greenStyle".equals(c4.getId());
        System.out.println("Test 4 (circle4 has #greenStyle id): "
                + (t4 ? "PASSED" : "FAILED"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}