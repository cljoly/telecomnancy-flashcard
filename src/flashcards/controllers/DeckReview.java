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
import javafx.stage.Stage;

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

    public void when_learn_button_is_clicked() {
        //TODO si le paquet ne contient plus de cartes à apprendre
        //TODO si nombre de cartes dans le paquet est égal à 0
        int nb_cartes_paquet = 0;
        int nb_cartes_restantes_a_apprendre = 1;
        if (nb_cartes_restantes_a_apprendre == 0) {

            new DispErrorPopup("Vous voulez vraiment vous acharner ?", "Vous avez déjà appris toutes les cartes contenues dans ce paquet.\n\n"
            + "Vous n'avez plus besoin de l'apprendre, vous êtes assez bon, veuillez sélectionner un autre paquet pour réviser");

            ((Stage) deck_review_window.getParent().getScene().getWindow()).close();

        } else if (nb_cartes_paquet == 0 ) {

            new DispErrorPopup("Paquet vide", "Ce paquet ne contient pas de cartes, veuillez ajouter des cartes à ce paquet afin de pouvoir commencer son apprentissage.\n"
            + "L'ajout de carte à un paquet s'effectue dans l'onglet \"Ajouter carte(s)\"");

            ((Stage) deck_review_window.getParent().getScene().getWindow()).close();

        } else {

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


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
