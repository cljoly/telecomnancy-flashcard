package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;

public class WhenADeckIsClicked implements EventHandler<ActionEvent> {

    private Button b;
    private VBox list_of_cards_container;


    private TextArea recto_details, verso_details;
    private Button add_change_to_card, unsave_changes, delete_card;
    private Label deck_name;
    private TextArea deck_description;

    public WhenADeckIsClicked(Button button, Label deck_name, TextArea deck_description, VBox list_of_cards_container, TextArea recto_details, TextArea verso_details, Button add_change_to_card, Button unsave_changes, Button delete_card) {
        this.b = button;
        this.list_of_cards_container = list_of_cards_container;
        this.recto_details = recto_details;
        this.verso_details = verso_details;
        this.add_change_to_card = add_change_to_card;
        this.unsave_changes = unsave_changes;
        this.delete_card = delete_card;
        this.deck_name = deck_name;
        this.deck_description = deck_description;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            String deckName = this.b.getText();
            User u = GameUsers.getInstance().getCurrentUser();
            Deck d = u.get_deck(deckName);
            List<Card> list_of_deck_cards = u.get_card_from_deck(d);
            list_of_cards_container.getChildren().clear();
            for (Card c : list_of_deck_cards){

                FXMLLoader path = new FXMLLoader();
                path.setLocation(getClass().getClassLoader().getResource("Show_a_card.fxml"));
                path.setControllerFactory(iC -> new Show_a_card(c, recto_details, verso_details, add_change_to_card, unsave_changes, delete_card));

                AnchorPane item = path.load();

                list_of_cards_container.getChildren().add(item);


            }

            this.deck_name.setText(deckName);
            this.deck_description.setText(d.getDescription());

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
