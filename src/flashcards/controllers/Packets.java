package flashcards.controllers;

import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Packets implements Initializable {

    private GameUsers g;
    private User currentUser;
    private List<Deck> listOfAllDecks;

    @FXML
    private TableColumn paquets_column;
    @FXML
    private TableView home_table;

    public Packets() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.g = GameUsers.getInstance();
        this.currentUser = this.g.getCurrentUser();
        paquets_column.setCellValueFactory(new PropertyValueFactory<>("nom"));



        Callback<TableColumn<Deck, String>, TableCell<Deck, String>> cellFactory
                = //
                new Callback<TableColumn<Deck, String>, TableCell<Deck, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Deck, String> param) {
                        final TableCell<Deck, String> cell = new TableCell<Deck, String>() {

                            final Button btn = new Button("Just Do It");

                            @Override
                            public void updateItem(String item, boolean empty) {

                                super.updateItem(item, empty);
                                btn.setText(item);
                                btn.setPrefWidth(450);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Deck deck = getTableView().getItems().get(getIndex());
                                        URL path = getClass().getClassLoader().getResource("Popup_learning.fxml");
                                        try{
                                            FXMLLoader loader = new FXMLLoader();
                                            loader.setLocation(getClass().getClassLoader().getResource("DeckReview.fxml"));
                                            loader.setControllerFactory(iC-> new DeckReview(deck));


                                            Stage learn = FXMLLoader.load(path);
                                            AnchorPane content = loader.load();
                                            ((BorderPane) learn.getScene().getRoot()).setCenter(content);
                                            learn.show();
                                        } catch (IOException e){
                                            e.printStackTrace();                                        }
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        paquets_column.setCellFactory(cellFactory);
        
        
        
        
        
        
        
        
        
        
        try{
            this.listOfAllDecks = this.currentUser.get_all_decks();
            for (Deck d : this.listOfAllDecks){
                home_table.getItems().addAll(d);
            }
        } catch (SQLException e){}
        //paquets.getItems().setAll("Aucun");

    }
}