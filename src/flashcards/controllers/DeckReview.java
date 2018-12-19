package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    public void when_learn_button_is_clicked(){
        //TODO : sera à enlever quand j'aurai les fonctions de Morgan de l'algo
        Card c = new Card("Hi", "Je", false);

       // FXMLLoader recto = new FXMLLoader();
       // recto.setLocation(getClass().getClassLoader().getResource("TestCardRecto.fxml"));
       // recto.setControllerFactory(iC-> new );

    }
}
