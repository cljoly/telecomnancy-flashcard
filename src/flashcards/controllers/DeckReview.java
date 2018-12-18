package flashcards.controllers;

import flashcards.model.Deck;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DeckReview implements Initializable {

    private Deck deck;
    @FXML
    private Label lbl_review_deck_name;

    public DeckReview(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_review_deck_name.setText(this.deck.getNom());


    }
}
