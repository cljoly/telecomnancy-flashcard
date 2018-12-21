package flashcards;


import flashcards.controllers.Packets;
import flashcards.model.*;
import flashcards.controllers.Tabs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

        Packets p = new Packets(root);
        FXMLLoader paquets = new FXMLLoader();
        URL paquet_url = getClass().getClassLoader().getResource("Packets.fxml");
        paquets.setLocation(paquet_url);
        paquets.setControllerFactory(iC -> p);
        root.setCenter(paquets.load());


        primaryStage.setTitle("Memscarlo");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.getScene().getStylesheets().add("mainStylesheet.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {

            GameUsers gameUser = GameUsers.getInstance();
            gameUser.newUser("laurnou");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        launch(args);
    }

}


