package flashcards.controllers;

import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PacketSearch implements Initializable {

    @FXML
    private VBox list_of_all_decks;

    private List<Deck> listOfAllDecks;
    private User currentUser;

    @FXML
    VBox list_of_cards_container;


    public PacketSearch(){
        this.currentUser = GameUsers.getInstance().getCurrentUser();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            this.listOfAllDecks = this.currentUser.get_all_decks();
            for (Deck d : this.listOfAllDecks){

                Button b = new Button();
                b.setText(d.getNom());
                b.setMinWidth(250);
                b.setPrefWidth(29);

                b.addEventHandler(ActionEvent.ACTION, new WhenADeckIsClicked(b, list_of_cards_container));

                list_of_all_decks.getChildren().add(b);
            }
        } catch (SQLException e){}

    }

}
