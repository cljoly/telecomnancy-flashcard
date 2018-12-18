package flashcards.controllers;

import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class DeckCreate {

    @FXML
    private TextField deck_name;
    @FXML
    private TextArea deck_description;
    @FXML
    private Label deck_create_msg;

    public DeckCreate(){}

    public void createDeck(){
        GameUsers g = GameUsers.getInstance();
        User user = g.getCurrentUser();
        try {
            user.create_deck(deck_name.getText(), deck_description.getText());
            System.out.println("Paquet créé et ajouté à la BD");
            deck_create_msg.setText("Paquet \"" + deck_name.getText() + "\" créé avec succès !");
        } catch (SQLException e){
            //TODO popup utilisateur
        }
        clean_fields();
    }


    public void clean_fields(){
        deck_name.clear();
        deck_description.clear();
    }




}
