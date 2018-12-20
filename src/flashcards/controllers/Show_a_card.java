package flashcards.controllers;

import flashcards.model.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Show_a_card implements Initializable {

    private Card card;
    @FXML
    private Button card_recto;
    @FXML
    private Label card_verso;

    public Show_a_card(Card c){
        this.card = c;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        card_recto.setText(this.card.getRecto());
        card_verso.setText(this.card.getVerso());


    }
}
