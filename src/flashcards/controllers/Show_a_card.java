package flashcards.controllers;

import flashcards.model.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Show_a_card implements Initializable {

    private Card card;
    @FXML
    private Button card_button;
    @FXML
    private Label card_recto;
    @FXML
    private Label card_verso;


    private TextArea recto_details, verso_details;
    private Button add_change_to_card, unsave_changes, delete_card;


    public Show_a_card(Card card, TextArea recto_details, TextArea verso_details, Button add_change_to_card, Button unsave_changes, Button delete_card) {
        this.card = card;
        this.recto_details = recto_details;
        this.verso_details = verso_details;
        this.add_change_to_card = add_change_to_card;
        this.unsave_changes = unsave_changes;
        this.delete_card = delete_card;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        card_recto.setText(this.card.getRecto());
        card_verso.setText(this.card.getVerso());


    }

    public void show_card_details(){

        this.recto_details.setText(this.card.getRecto());
        this.verso_details.setText(this.card.getVerso());

    }
}
