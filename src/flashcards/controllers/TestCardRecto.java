package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Training;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TestCardRecto implements Initializable {

    private Card card;
    private Training training;

    @FXML
    private Button btn_test_card_show;
    @FXML
    private Label lbl_test_card_recto_a;
    @FXML
    private AnchorPane deck_recto_window;

    public TestCardRecto(Training training){
        this.training = training;
        if (this.training != null) {
            this.card = this.training.go_to_next_card();
        } else {
            this.card = null;
        }
    }

    public void when_show_answer_is_clicked(){
        try {

            FXMLLoader verso = new FXMLLoader();
            verso.setLocation(getClass().getClassLoader().getResource("TestCardVerso.fxml"));
            verso.setControllerFactory(iC -> new TestCardVerso(this.training, this.card));

            //chargement de l'anchor pane deck review
            AnchorPane content = verso.load();

            //lien
            ((BorderPane) deck_recto_window.getParent().getScene().getRoot()).setCenter(content);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.training != null){
            lbl_test_card_recto_a.setText(this.card.getRecto());
        } else {
            lbl_test_card_recto_a.setText("Ne marche pas");
        }
    }
}
