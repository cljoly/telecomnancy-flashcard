package flashcards;


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


        path = getClass().getClassLoader().getResource("Packets.fxml");
        Parent decks = FXMLLoader.load(path);
        root.setCenter(decks);

        primaryStage.setTitle("Memscarlo");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1080, 720));
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


