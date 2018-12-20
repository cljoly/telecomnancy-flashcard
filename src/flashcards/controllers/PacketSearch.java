package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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
    private VBox list_of_cards_container;

    @FXML
    private TextArea recto_details;

    @FXML
    private TextArea verso_details;

    @FXML
    private Button add_change_to_card;

    @FXML
    private Button unsave_changes;

    @FXML
    private Button delete_card;


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

                b.addEventHandler(ActionEvent.ACTION, new WhenADeckIsClicked(b, list_of_cards_container, recto_details, verso_details, add_change_to_card, unsave_changes, delete_card));

                list_of_all_decks.getChildren().add(b);
            }
        } catch (SQLException e){}

    }

    public void disp_all_cards(){
        try{
            list_of_cards_container.getChildren().clear();
            List<Card> list_of_all_cards = GameUsers.getInstance().getCurrentUser().get_all_cards();
            for (Card c : list_of_all_cards){

                FXMLLoader path = new FXMLLoader();
                path.setLocation(getClass().getClassLoader().getResource("Show_a_card.fxml"));
                path.setControllerFactory(iC -> new Show_a_card(c, recto_details, verso_details, add_change_to_card, unsave_changes, delete_card));

                AnchorPane item = path.load();

                list_of_cards_container.getChildren().add(item);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void add_the_changes_to_the_card(){
        try {
            Card c = this.currentUser.get_card_recto(recto_details.getText());
            this.currentUser.change_verso_of_card(c, verso_details.getText());
            System.out.println(c.toString());

            recto_details.clear();
            verso_details.clear();

            list_of_cards_container.getChildren().clear();

        } catch(Exception e){
            e.printStackTrace();
        }


    }

    public void cancel_and_show_before(){
        try {
            Card c = this.currentUser.get_card_recto(recto_details.getText());
            verso_details.setText(c.getVerso());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void delete_card(){

    }

}
