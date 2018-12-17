package flashcards;

import flashcards.controllers.Tabs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class App extends Application {

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
        launch(args);
    }



}
