package flashcards.controllers;

import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

import static java.lang.Boolean.FALSE;

public class AddCard
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
    private TextField recto_content;
    @FXML
    private TextField verso_content;
    @FXML
    ToggleGroup reverse;

    public AddCard()
    {
        //final ToggleGroup group = new ToggleGroup();
    }

    public void createCard(){
        GameUsers g = GameUsers.getInstance();
        User user = g.getCurrentUser();
        try {
            user.create_card(recto_content.getText(), verso_content.getText(), reverse.getSelectedToggle() == oui);
            System.out.println("carte ajoutée");
        } catch (SQLException e){
            //TODO popup utilisateur
        }
    }

}
