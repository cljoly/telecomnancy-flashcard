package flashcards.controllers;

import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private BorderPane root;

    @FXML
    private TableColumn paquets_column;
    @FXML
    private TableView home_table;

    public Packets(BorderPane root) {
        this.root = root;
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

                                        //URL path = getClass().getClassLoader().getResource("Popup_learning.fxml");

                                        FXMLLoader popup = new FXMLLoader();
                                        popup.setLocation(getClass().getClassLoader().getResource("Popup_learning.fxml"));
                                        popup.setControllerFactory(iC-> new PopupLearning(deck));


                                        try{
                                            FXMLLoader loader = new FXMLLoader();
                                            loader.setLocation(getClass().getClassLoader().getResource("DeckReview.fxml"));
                                            loader.setControllerFactory(iC-> new DeckReview(deck));

                                            //chargement du stage popup
                                            //Stage learn = popup.load();
                                            Stage s = new Stage();
                                            s.setTitle("Apprentissage d'un paquet");
                                            s.setResizable(false);
                                            s.setScene(new Scene(popup.load(), 1080, 720));


                                            //chargement de l'anchor pane deck review
                                            AnchorPane content = loader.load();

                                            //lien entre popup et deck review
                                            ((BorderPane) s.getScene().getRoot()).setCenter(content);
                                            s.show();
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
