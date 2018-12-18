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
    private Label card_create_msg;
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
            String msg = ("Recto : \n" + recto_content.getText() + "\n\nVerso : \n" +verso_content.getText());
            new DispSuccessPopup("La carte a été ajoutée avec succès", msg);
            clean_fields();
        } catch (SQLException e){
            new DispErrorPopup("Erreur de création de la carte", "Une carte déjà existante comporte les mêmes informations, soit dans son recto, soit dans son verso. Sa création est donc impossible.");
        }
    }

    public void clean_fields(){
        recto_content.clear();
        verso_content.clear();
        non.setSelected(true);
        //TODO : resest paquet choisi à "aucun"


    }



}
