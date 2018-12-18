package flashcards.controllers;

import javafx.scene.control.Alert;

public class DispSuccessPopup {

    public DispSuccessPopup(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fenêtre d'information");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();
    }



}
