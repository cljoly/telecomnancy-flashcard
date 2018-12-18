package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;
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
    @FXML
    ComboBox paquets;

    private GameUsers g;
    private User currentUser;
    private List<Deck> listOfAllDecks;

    public AddCard()
    {
        this.g = GameUsers.getInstance();
        currentUser = this.g.getCurrentUser();

        //ajouter tous les decks au combobox
        //TODO : voir si l'instanciation dans le constructeur permet la MAJ lors de l'ajout d'un paquet, si non : faire autre part
        /*this.listOfAllDecks = this.currentUser.get_all_decks();
        for (Deck d : this.listOfAllDecks){
            paquets.getItems().add(d.getNom());
        }*/

    }

    public void createCard(){

        try {
            Card c = this.currentUser.create_card(recto_content.getText(), verso_content.getText(), reverse.getSelectedToggle() == oui);
            //Deck d = user.get_deck(combo sélectionné);
            //user.add_card2deck(c, d);
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
