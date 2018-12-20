package flashcards.controllers;

import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;

public class WhenADeckIsClicked implements EventHandler<ActionEvent> {

    private Button b;
    private VBox list_of_cards_container;

    public WhenADeckIsClicked(Button button, VBox list_of_cards_container) {
        this.b = button;
        this.list_of_cards_container = list_of_cards_container;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            String deck_name = this.b.getText();
            User u = GameUsers.getInstance().getCurrentUser();
            Deck d = u.get_deck(deck_name);
            List<Card> list_of_deck_cards = u.get_card_from_deck(d);
            list_of_cards_container.getChildren().clear();
            for (Card c : list_of_deck_cards){

                FXMLLoader path = new FXMLLoader();
                path.setLocation(getClass().getClassLoader().getResource("Show_a_card.fxml"));
                path.setControllerFactory(iC -> new Show_a_card(c));

                AnchorPane item = path.load();

                list_of_cards_container.getChildren().add(item);


            }

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
