package flashcards.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCard implements Initializable
{
    @FXML
    private Label state;
    @FXML
    private RadioButton oui;
    @FXML
    private RadioButton non;
    @FXML
    private Button ajout;
    @FXML
    private Button annul;
    @FXML
    private ComboBox<String> packet;

    public AddCard()
    {
        //final ToggleGroup group = new ToggleGroup();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        packet.getItems().setAll("ok","bonjour");
    }
}
