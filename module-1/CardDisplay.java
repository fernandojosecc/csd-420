/**
 * Author: Fernando Contreras
 * Course: CSD 420 Advanced Java Programming
 * Assignment: Module 1 Programming Assignment - Random Card Display
 * Date: 17 August 2026
 *
 * Description:
 * JavaFX application that displays four randomly selected cards from a
 * standard 52-card deck. A refresh button below the cards allows the
 * user to draw a new set of four cards. Event handling is implemented
 * using a lambda expression as required by the assignment.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardDisplay extends Application {

    // Total number of cards in a standard deck and the number to display
    private static final int DECK_SIZE = 52;
    private static final int CARDS_TO_DISPLAY = 4;

    // Display dimensions for each card image, in pixels
    private static final double CARD_WIDTH = 120;
    private static final double CARD_HEIGHT = 180;

    // ImageView slots are created once and reused on refresh so the
    // scene graph does not need to be rebuilt every time
    private final ImageView[] cardViews = new ImageView[CARDS_TO_DISPLAY];

    @Override
    public void start(Stage primaryStage) {
        // Horizontal container that lays the four cards side by side
        HBox cardRow = new HBox(15);
        cardRow.setAlignment(Pos.CENTER);
        cardRow.setPadding(new Insets(20));

        // Build the four ImageView slots and add them to the row
        for (int i = 0; i < CARDS_TO_DISPLAY; i++) {
            cardViews[i] = new ImageView();
            cardViews[i].setFitWidth(CARD_WIDTH);
            cardViews[i].setFitHeight(CARD_HEIGHT);
            cardViews[i].setPreserveRatio(true);
            cardRow.getChildren().add(cardViews[i]);
        }

        // Refresh button positioned below the cards
        Button refreshButton = new Button("Refresh");
        refreshButton.setPrefWidth(120);

        // Lambda expression handles the button click by drawing four new cards
        refreshButton.setOnAction(event -> drawCards());

        // Root layout stacks the card row above the refresh button
        VBox root = new VBox(15, cardRow, refreshButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Initial draw so the window opens with four cards already showing
        drawCards();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Card Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Randomly selects four unique cards from the deck and loads their
     * images into the ImageView slots. Shuffling a list of card numbers
     * and taking the first four guarantees no duplicates in the hand.
     */
    private void drawCards() {
        // Build a list containing card numbers 1 through 52
        List<Integer> deck = new ArrayList<>();
        for (int i = 1; i <= DECK_SIZE; i++) {
            deck.add(i);
        }

        // Shuffle randomizes the order using the default random source
        Collections.shuffle(deck);

        // Load the first four shuffled cards from the cards subdirectory
        for (int i = 0; i < CARDS_TO_DISPLAY; i++) {
            int cardNumber = deck.get(i);
            String imagePath = "cards/" + cardNumber + ".png";
            Image cardImage = new Image("file:" + imagePath);
            cardViews[i].setImage(cardImage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}