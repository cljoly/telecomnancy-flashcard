package flashcards.controllers;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class DispErrorPopup {

    public DispErrorPopup(String titre, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fenêtre d'erreur");
        alert.setHeaderText(titre);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }


}
