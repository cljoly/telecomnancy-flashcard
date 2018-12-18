package flashcards.controllers;

import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

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
    private TextArea recto_content;
    @FXML
    private TextArea verso_content;
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
            //TODO changer message quand carte ajoutée, embêtant car utilise des threads
        } catch (SQLException e){
            //TODO popup utilisateur
        }
        clean_fields();
    }

    public void clean_fields(){
        recto_content.clear();
        verso_content.clear();
        non.setSelected(true);
        //TODO : resest paquet choisi à "aucun"


    }



}
