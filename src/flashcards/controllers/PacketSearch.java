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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
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

    private BorderPane root;

    @FXML
    private Label deck_name;
    @FXML
    private TextArea deck_description;


    public PacketSearch(BorderPane root){
        this.currentUser = GameUsers.getInstance().getCurrentUser();
        this.root = root;
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
                b.addEventHandler(ActionEvent.ACTION, new WhenADeckIsClicked(b, deck_name, deck_description, list_of_cards_container, recto_details, verso_details, add_change_to_card, unsave_changes, delete_card));

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

            this.deck_name.setText("Aucun paquet sélectionné");
            this.deck_description.setText("Veuillez sélectionner un paquet spécifique pour en\n" + "afficher sa description");

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void add_the_changes_to_the_card(){
        try {
            if (recto_details.getText().isEmpty()){

            } else {
                Card c = this.currentUser.get_card_recto(recto_details.getText());
                this.currentUser.change_verso_of_card(c, verso_details.getText());
                System.out.println(c.toString());

                recto_details.clear();
                verso_details.clear();

                list_of_cards_container.getChildren().clear();
            }

        } catch(Exception e){
            e.printStackTrace();
        }


    }

    public void cancel_and_show_before(){
        try {
            if (recto_details.getText().isEmpty()) {

            } else {

                Card c = this.currentUser.get_card_recto(recto_details.getText());
                verso_details.setText(c.getVerso());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void delete_card_from_app(){
        try {

            if (recto_details.getText().isEmpty()){

            } else {
                Card c = this.currentUser.get_card_recto(recto_details.getText());

                this.currentUser.delete_card_and_its_associations(c);

                list_of_cards_container.getChildren().clear();

                recto_details.clear();
                verso_details.clear();

                new DispSuccessPopup("Suppression", ("Votre carte possédant le contenu suivant a bien été supprimée : \n"
                        + "Recto : \n" + c.getRecto() + "\n\nVerso : \n" + c.getVerso()));
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }


    public void delete_selected_deck(){
        try {

            if (deck_name.getText().equals("Aucun paquet sélectionné")){

                GameUsers.getInstance().getCurrentUser().delete_all_data();

                URL path = getClass().getClassLoader().getResource("PacketSearch.fxml");
                FXMLLoader deck = new FXMLLoader();
                deck.setLocation(path);
                deck.setControllerFactory(iC -> new PacketSearch(this.root));
                //Parent decks = FXMLLoader.load(path);
                this.root.setCenter(deck.load());

                new DispSuccessPopup("Suppression", "Tous les paquets viennent d'être supprimés");


            } else if (deck_name.getText().isEmpty()){

            } else {
                Deck d = GameUsers.getInstance().getCurrentUser().get_deck(deck_name.getText());
                GameUsers.getInstance().getCurrentUser().delete_deck_and_its_cards(d);
                URL path = getClass().getClassLoader().getResource("PacketSearch.fxml");
                FXMLLoader deck = new FXMLLoader();
                deck.setLocation(path);
                deck.setControllerFactory(iC -> new PacketSearch(this.root));
                //Parent decks = FXMLLoader.load(path);
                this.root.setCenter(deck.load());

                new DispSuccessPopup("Suppression", ("Votre paquet " + d.getNom() + " a bien été supprimé."));
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    public void export_the_deck(){
        try {
            if (deck_name.getText().equals("Aucun paquet sélectionné")){
                new DispErrorPopup("Export impossible", "Vous ne pouvez pas exporter un paquet sans l'avoir sélectionné");
            } else if (deck_name.getText().isEmpty()){

            } else {
                Deck d = GameUsers.getInstance().getCurrentUser().get_deck(deck_name.getText());
                String json = GameUsers.getInstance().getCurrentUser().export_card_in_deck(d);
                String nom_fichier = "Export_paquets/" + d.getNom() + ".json";
                GameUsers.getInstance().getCurrentUser().save_file(nom_fichier, json);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
