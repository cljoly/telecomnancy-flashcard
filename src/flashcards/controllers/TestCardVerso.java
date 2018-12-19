package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Faces;
import flashcards.model.Training;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TestCardVerso implements Initializable{

    private Training training;
    private Card card;

    @FXML
    private Label lbl_test_card_verso_a;
    @FXML
    private Label lbl_test_card_verso_b;
    @FXML
    private AnchorPane deck_verso_window;

    public TestCardVerso(Training training, Card card){
        this.training = training;
        this.card = card;
    }


    public void when_a_frimousse_is_clicked(ActionEvent actionEvent){
        try {
            String frimousse = ((Button) actionEvent.getSource()).getId();
            switch (frimousse) {
                case "smile": {
                    this.training.save_answer(this.card, Faces.FaceSmile);
                    break;
                }
                case "neutral": {
                    this.training.save_answer(this.card, Faces.FaceNeutral);
                    break;
                }
                case "frown": {
                    this.training.save_answer(this.card, Faces.FaceFrown);
                    break;
                }
                default:
                    break;
            }

            if (!this.training.isFinished()) {

                FXMLLoader recto = new FXMLLoader();
                recto.setLocation(getClass().getClassLoader().getResource("TestCardRecto.fxml"));
                recto.setControllerFactory(iC -> new TestCardRecto(this.training));

                //chargement de l'anchor pane deck review
                AnchorPane content = recto.load();

                //lien entre popup et deck review
                ((BorderPane) deck_verso_window.getParent().getScene().getRoot()).setCenter(content);

            } else {

                System.out.println("Apprentissage terminé");
                new DispSuccessPopup("Apprentissage du paquet terminé", "Bravo, vous avez terminé l'apprentissage du paquet "
                + this.training.getDeck().getNom() + " !\n"
                + "Bon travail !");

                ((Stage) deck_verso_window.getParent().getScene().getWindow()).close();
            }

        } catch(Exception e){
            e.printStackTrace();
        }

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
