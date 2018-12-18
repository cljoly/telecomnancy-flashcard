package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static java.lang.Boolean.FALSE;

public class AddCard implements Initializable{
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
    private ToggleGroup reverse;
    @FXML
    private ComboBox<String> paquets;

    private GameUsers g;
    private User currentUser;
    private List<Deck> listOfAllDecks;

    public AddCard()
    {


        //ajouter tous les decks au combobox
        //TODO : voir si l'instanciation dans le constructeur permet la MAJ lors de l'ajout d'un paquet, si non : faire autre part



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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.g = GameUsers.getInstance();
        currentUser = this.g.getCurrentUser();

        try{
            this.listOfAllDecks = this.currentUser.get_all_decks();
            for (Deck d : this.listOfAllDecks){
                System.out.println(d.getNom());
                paquets.getItems().addAll(d.getNom());
            }
        } catch (SQLException e){}
        //paquets.getItems().setAll("Aucun");
    }
}
