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

    /**
     * Réaction au clic sur un bouton permettant d'afficher l'onglet de présentations des paquets
     * @throws Exception
     */
    public void BTN_DECKS_ON_ACTION() throws Exception{
        System.out.println("decks");

        URL path;
        path = getClass().getClassLoader().getResource("Packets.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);
    }

    /**
     * Réaction au clic sur un bouton permettant d'afficher l'onglet d'ajout d'une nouvelle cartes
     * @throws Exception
     */
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
    /**
     * Réaction au clic sur un bouton permettant d'afficher l'onglet de la recherche de cartes
     * @throws Exception
     */
    public void BTN_BROWSE_ON_ACTION() throws Exception{
        System.out.println("Browse decks");

        URL path;
        path = getClass().getClassLoader().getResource("PacketSearch.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);
    }
    /**
     * Réaction au clic sur un bouton permettant d'afficher l'onglet de présentation des statistiques
     * @throws Exception
     */
    public void BTN_STATS_ON_ACTION(){
        System.out.println("UserStats");
    }
    /**
     * Réaction au clic sur un bouton permettant d'afficher l'onglet de création de pacquets
     * @throws Exception
     */
    public void BTN_CREATE_DECK_ON_ACTION() throws Exception{
        System.out.println("Create deck");

        URL path;
        path = getClass().getClassLoader().getResource("DeckCreate.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);
    }
}
