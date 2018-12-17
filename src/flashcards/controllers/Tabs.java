package flashcards.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class Tabs {
    BorderPane root;

    public Tabs(BorderPane root){
        this.root = root;
    }

    public void BTN_DECKS_ON_ACTION() throws Exception{
        System.out.println("decks");

        URL path;
        path = getClass().getClassLoader().getResource("Packets.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);
    }

    public void BTN_ADD_ON_ACTION() throws Exception{
        System.out.println("Add card");

        URL path;
        path = getClass().getClassLoader().getResource("AddCard.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(path);
        loader.setControllerFactory(iC -> new AddCard());
        //Parent decks = FXMLLoader.load(path);
        root.setCenter(loader.load());
    }

    public void BTN_BROWSE_ON_ACTION() throws Exception{
        System.out.println("Browse decks");

        URL path;
        path = getClass().getClassLoader().getResource("PacketSearch.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);
    }

    public void BTN_STATS_ON_ACTION(){
        System.out.println("Stats");
    }

    public void BTN_CREATE_DECK_ON_ACTION() throws Exception{
        System.out.println("Create deck");

        URL path;
        path = getClass().getClassLoader().getResource("DeckCreate.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);
    }
}
