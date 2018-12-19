package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Training;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TestCardVerso implements Initializable{

    private Training training;
    private Card card;

    @FXML
    private Label lbl_test_card_verso_a;
    @FXML
    private Label lbl_test_card_verso_b;

    public TestCardVerso(Training training, Card card){
        this.training = training;
        this.card = card;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.training != null && this.card != null){
            lbl_test_card_verso_a.setText(this.card.getRecto());
            lbl_test_card_verso_b.setText(this.card.getVerso());
        } else {
            //Card c = new Card("Toto", "Tata", false);
            //String recto = c.getRecto();
            lbl_test_card_verso_a.setText("Ne marche pas");
            lbl_test_card_verso_b.setText("Ne marche pas");
        }

    }
}
