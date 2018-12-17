package flashcards;


import flashcards.model.Deck;
import flashcards.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class App extends Application {

    public final static String DATABASE_URL = "jdbc:h2:file:./database/sample";

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL path = getClass().getClassLoader().getResource("AppView.fxml");
        Parent root = FXMLLoader.load(path);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            User u1 = new User("lau2do");
            Deck d1 = u1.create_deck("Anglais2", "Ne sert à rien");
            System.out.println(u1.get_deck(d1.getNom()));
            System.out.println(u1.get_deck("Anglais"));
        } catch (Exception e) {
        }

        launch(args);
    }



}


