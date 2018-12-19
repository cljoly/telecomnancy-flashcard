package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.Training;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeckReview implements Initializable {

    private Deck deck;
    @FXML
    private Label lbl_review_deck_name;
    @FXML
    private AnchorPane deck_review_window;

    public DeckReview(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbl_review_deck_name.setText(this.deck.getNom());


    }

    public void when_learn_button_is_clicked(){
        //TODO si nombre de cartes du deck restant à apprendre est égal à 0, on ne permet pas de lancer l'apprentissage : popup, et on revient sur l'onglet principal

        try {

            GameUsers.getInstance().getCurrentUser().createNewTraining(this.deck);
            Training training = GameUsers.getInstance().getCurrentUser().getCurrentTraining();

            FXMLLoader recto = new FXMLLoader();
            recto.setLocation(getClass().getClassLoader().getResource("TestCardRecto.fxml"));
            recto.setControllerFactory(iC -> new TestCardRecto(training));

            //chargement de l'anchor pane deck review
            AnchorPane content = recto.load();

            //lien entre popup et deck review
            ((BorderPane) deck_review_window.getParent().getScene().getRoot()).setCenter(content);


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
