package flashcards.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpPopup implements Initializable {
    @FXML public WebView wv_help;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File f = new File("res/interface/html/help.html");

        try {
            wv_help.getEngine().load(f.toURI().toURL().toString());

        } catch (Exception e) {

        }


    }
}
