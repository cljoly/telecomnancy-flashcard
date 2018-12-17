package flashcards;


import flashcards.model.Card;
import flashcards.model.Deck;
import flashcards.model.User;
import flashcards.controllers.Tabs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

import static java.lang.Boolean.FALSE;

public class App extends Application {

    public final static String DATABASE_URL = "jdbc:h2:file:./database/sample";

    @Override

    public void start(Stage primaryStage) throws Exception {
        URL path;

        BorderPane root = new BorderPane();

        Tabs o = new Tabs(root);

        FXMLLoader loader = new FXMLLoader();
        URL coucou = getClass().getClassLoader().getResource("Tabs.fxml");
        loader.setLocation(coucou);
        loader.setControllerFactory(iC -> o);
        root.setTop(loader.load());


        path = getClass().getClassLoader().getResource("Packets.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);

        primaryStage.setTitle("Memscarlo");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            User u1 = new User("lau2do");
            Deck d1 = u1.create_deck("Anglais", "Ne sert à rien");
            Deck d2 = u1.create_deck("Anglais2", "Ne sert à rien du tout");
            System.out.println(u1.get_deck(d1.getNom()));
            System.out.println(u1.get_deck("Anglais"));

            //Card c1 = u1.create_card("France", "Paris", false);
            Card c2 = u1.create_card("Islande", "Reykjavic", false);
            Card c3 = u1.create_card("Australie", "Canberra", false);
            Card c4 = u1.create_card("Russie", "Moscou", false);

            //u1.add_card2deck(c1, d1);
            u1.add_card2deck(c2, d1);
            u1.add_card2deck(c3, d1);
            u1.add_card2deck(c4, d1);

            System.out.println(u1.get_card_recto("Russie"));
            System.out.println(u1.get_card_recto("France"));


            Card c5 = u1.create_card("Miel", "Honey", FALSE);
            Card c6 = u1.create_card("Bonjour", "Hello", FALSE);
            System.out.println(u1.get_card_recto("Bonjour"));
            System.out.println(u1.get_card_recto("Miel"));


        } catch (Exception e) {
        }

        launch(args);
    }



}


