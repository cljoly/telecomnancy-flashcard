package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Training;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TestCardRecto implements Initializable {

    private Card card;
    private Training training;

    @FXML
    private Button btn_test_card_show;
    @FXML
    private Label q_recto_content;

    public TestCardRecto(Training training){
        this.training = training;
    }

    public void when_show_answer_is_clicked(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.training != null) {
            Card c = this.training.go_to_next_card();
            q_recto_content.setText(c.getRecto());
        } else {
        //Card c = new Card("Toto", "Tata", false);
        //String recto = c.getRecto();
        q_recto_content.setText("Ne marche pas");
        }
    }
}
