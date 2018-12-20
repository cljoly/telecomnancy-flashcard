package flashcards.controllers;

import flashcards.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeckReview implements Initializable {

    private Deck deck;
    @FXML
    private Label lbl_review_deck_name;
    @FXML
    private AnchorPane deck_review_window;
    @FXML
    private Label lbl_review_new_card_count;
    @FXML
    private Label lbl_review_learning_card_count;
    @FXML
    private Label lbl_review_learnt_card_count;

    public DeckReview(Deck deck) {
        this.deck = deck;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            User u = GameUsers.getInstance().getCurrentUser();

            lbl_review_deck_name.setText(this.deck.getNom());

            int inedites = u.get_deck_stats_about_cards(this.deck, CardStates.NotSeen);
            lbl_review_new_card_count.setText(String.valueOf(inedites));

            int en_cours = u.get_deck_stats_about_cards(this.deck, CardStates.Learning);
            lbl_review_learning_card_count.setText(String.valueOf(en_cours));

            int apprises = u.get_deck_stats_about_cards(this.deck, CardStates.Learned);
            lbl_review_learnt_card_count.setText(String.valueOf(apprises));



        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void when_learn_button_is_clicked() {
        try {

            User u = GameUsers.getInstance().getCurrentUser();

            int nb_cartes_paquet = 0;

            if (u.isLearned(this.deck) && u.get_deck_cards_number(this.deck) != 0) {

                new DispErrorPopup("Vous voulez vraiment vous acharner ?", "Vous avez déjà appris toutes les cartes contenues dans ce paquet.\n\n"
                        + "Vous n'avez plus besoin de l'apprendre, vous êtes assez bon, veuillez sélectionner un autre paquet pour réviser");

                ((Stage) deck_review_window.getParent().getScene().getWindow()).close();

            } else if (u.get_deck_cards_number(this.deck) == 0) {

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
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
