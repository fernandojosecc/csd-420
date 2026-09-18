/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 8 Programming Assignment - Multithreading
 * Date: September 17th 2026
 *
 * Description:
 * JavaFX application that uses three concurrent threads to generate and
 * display three different categories of characters in a shared TextArea:
 *   - Thread 1: random lowercase letters (a-z)
 *   - Thread 2: random digits (0-9)
 *   - Thread 3: random special characters (!@#$%&*)
 *
 * Each thread produces 10,000 characters. Because the threads run in
 * parallel and append their characters to the TextArea one at a time
 * as they are generated, the output appears interleaved rather than
 * grouped by thread. This is the point of the assignment: it shows
 * the OS scheduler switching between threads in real time.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Random;

public class FernieThreeThreads extends Application {

    // Number of characters each thread produces
    private static final int CHARS_PER_THREAD = 10_000;

    // Character pools for each thread
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%&*";

    // Shared TextArea that all three threads append into
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);

        BorderPane root = new BorderPane();
        root.setCenter(outputArea);

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setTitle("Fernie ThreeThreads");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Run the built-in tests first so their results print before the
        // threads flood the console with anything else
        runTests();

        // Kick off all three character-producing threads. They run
        // concurrently, so the TextArea output will be interleaved.
        Thread letterThread = new Thread(new CharacterProducer(LETTERS), "LetterThread");
        Thread digitThread = new Thread(new CharacterProducer(DIGITS), "DigitThread");
        Thread symbolThread = new Thread(new CharacterProducer(SYMBOLS), "SymbolThread");

        letterThread.start();
        digitThread.start();
        symbolThread.start();
    }

    /**
     * Runnable task that generates CHARS_PER_THREAD random characters
     * from the given pool and appends each one to the shared TextArea.
     * Runnable is used instead of extending Thread so the producer stays
     * decoupled from thread management.
     */
    private class CharacterProducer implements Runnable {
        private final String pool;
        private final Random random = new Random();

        public CharacterProducer(String pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            for (int i = 0; i < CHARS_PER_THREAD; i++) {
                // Pick one random character from this thread's pool
                char c = pool.charAt(random.nextInt(pool.length()));

                // TextArea can only be safely modified on the JavaFX
                // Application Thread, so schedule the append there
                Platform.runLater(() -> outputArea.appendText(String.valueOf(c)));
            }
        }
    }

    /**
     * Basic tests that verify the character pools contain only the
     * expected categories of characters. These run on the main thread
     * before any producer threads start.
     */
    private void runTests() {
        System.out.println("--- Running tests ---");

        boolean t1 = LETTERS.chars().allMatch(Character::isLowerCase);
        System.out.println("Test 1 (LETTERS pool is all lowercase letters): "
                + (t1 ? "PASSED" : "FAILED"));

        boolean t2 = DIGITS.chars().allMatch(Character::isDigit);
        System.out.println("Test 2 (DIGITS pool is all digits): "
                + (t2 ? "PASSED" : "FAILED"));

        boolean t3 = SYMBOLS.chars().noneMatch(Character::isLetterOrDigit);
        System.out.println("Test 3 (SYMBOLS pool has no letters or digits): "
                + (t3 ? "PASSED" : "FAILED"));

        boolean t4 = CHARS_PER_THREAD >= 10_000;
        System.out.println("Test 4 (each thread produces at least 10,000 chars): "
                + (t4 ? "PASSED" : "FAILED"));

        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}