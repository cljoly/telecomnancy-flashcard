package flashcards.controllers;

import flashcards.model.Deck;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PopupLearning {

    private Deck deck;
    @FXML
    BorderPane popup_root;
    @FXML private javafx.scene.control.Button closeButton;

    public PopupLearning(Deck deck){
        this.deck = deck;

    }


    public void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();

    }


}
