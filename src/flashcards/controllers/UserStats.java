package flashcards.controllers;

import flashcards.model.CardStates;
import flashcards.model.Deck;
import flashcards.model.GameUsers;
import flashcards.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserStats implements Initializable {
    private GameUsers g;
    private User currentUser;
    private List<Deck> listOfAllDecks;


    @FXML private BarChart<String, Number> chart_cards_per_day;
    @FXML private Label lbl_cards_per_day_total;
    @FXML private Label lbl_cards_per_day_avg;
    @FXML private PieChart chart_cards_per_type;
    @FXML private Label lbl_cards_per_type_total;
    @FXML private ComboBox<String> cb_deck;

    /**
     * Handler pour la sélection du deck source
     * @throws Exception
     */
    public void btn_select_source_on_action() throws Exception{
        String nom_deck = cb_deck.getSelectionModel().getSelectedItem();
        Deck d = this.currentUser.get_deck(nom_deck);
        int total = currentUser.get_deck_stats_about_cards(d, CardStates.NotSeen)
                + currentUser.get_deck_stats_about_cards(d, CardStates.Learning)
                + currentUser.get_deck_stats_about_cards(d, CardStates.Learned);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.add(new PieChart.Data("Non vu", currentUser.get_deck_stats_about_cards(d, CardStates.NotSeen)));
        pieChartData.add(new PieChart.Data("En cours d'apprentissage", currentUser.get_deck_stats_about_cards(d, CardStates.Learning)));
        pieChartData.add(new PieChart.Data("Aquis", currentUser.get_deck_stats_about_cards(d, CardStates.Learned)));
        chart_cards_per_type.setData(pieChartData);
        lbl_cards_per_type_total.setText(String.valueOf(total));
    }

    /**
     * Initialise le controlleur avec les données de l'utilisateur
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //////Tests//////
        /*
        ArrayList<Pair<String, Integer>> cardNbPerDay = new ArrayList<Pair<String, Integer>>();
        cardNbPerDay.add(new Pair<>("J-7",15));
        cardNbPerDay.add(new Pair<>("J-6",12));
        cardNbPerDay.add(new Pair<>("J-5",16));
        cardNbPerDay.add(new Pair<>("J-4",28));
        cardNbPerDay.add(new Pair<>("J-3",5));
        cardNbPerDay.add(new Pair<>("J-2",12));
        cardNbPerDay.add(new Pair<>("J-1",13));
        cardNbPerDay.add(new Pair<>("J",4));
        */

        /*
        ArrayList<Pair<String, Integer>> cardNbPerType = new ArrayList<Pair<String, Integer>>();
        cardNbPerType.add(new Pair<>("Non vu", 50));
        cardNbPerType.add(new Pair<>("En cours d'apprentissage", 168));
        cardNbPerType.add(new Pair<>("Aquis", 49));
        */

        ///////Recovering user data////////////
        this.g = GameUsers.getInstance();
        this.currentUser = this.g.getCurrentUser();

        ArrayList<Pair<String, Integer>> cardNbPerDay;
        ArrayList<Pair<String, Integer>> cardNbPerType;

        int total;

        try {
            cardNbPerDay = currentUser.get_all_nbcard_days();
            cardNbPerType = currentUser.get_all_nbcard_type();
            //System.out.println("DEBUG DEBUG cardnb per day 0 : "+ cardNbPerDay.get(0).getKey()+ ", with value: "+ cardNbPerDay.get(0).getValue());
            //System.out.println("DEBUG DEBUG cardnb per type 0 : "+ cardNbPerType.get(0).getKey()+ ", with value: "+ cardNbPerType.get(0).getValue());
            //System.out.println("DEBUG DEBUG cardnb per type 1 : "+ cardNbPerType.get(1).getKey()+ ", with value: "+ cardNbPerType.get(1).getValue());
            //System.out.println("DEBUG DEBUG cardnb per type 2 : "+ cardNbPerType.get(2).getKey()+ ", with value: "+ cardNbPerType.get(2).getValue());


            ///////Initialising cards per day graph///////////
            chart_cards_per_day.getXAxis().setLabel("Date");
            chart_cards_per_day.getYAxis().setLabel("Nombre de visite");
            total = 0;
            int moyenne;

            XYChart.Series series1 = new XYChart.Series();
            for (Pair<String, Integer> p: cardNbPerDay){
                total = total + p.getValue();
                series1.getData().add(new XYChart.Data(p.getKey(), p.getValue()));
            }
            if (cardNbPerDay.size() != 0) {moyenne = total/cardNbPerDay.size();}
            else {moyenne = 0;}

            chart_cards_per_day.getData().add(series1);
            chart_cards_per_day.setLegendVisible(false);
            lbl_cards_per_day_avg.setText(String.valueOf(moyenne));
            lbl_cards_per_day_total.setText(String.valueOf(total));


            ///////Initialising cards per type graph//////////
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            total = 0;
            String type;
            for (Pair<String,Integer> p: cardNbPerType){
                type = p.getKey() + " : " + String.valueOf(p.getValue());
                pieChartData.add(new PieChart.Data(type, p.getValue()));
                total = total + p.getValue();
            }
            lbl_cards_per_type_total.setText(String.valueOf(total));
            chart_cards_per_type.setLegendVisible(false);
            chart_cards_per_type.setData(pieChartData);

            /////// Linking combo box to decks ////////////
            this.listOfAllDecks = this.currentUser.get_all_decks();
            for (Deck d : this.listOfAllDecks){
                System.out.println(d.getNom());
                cb_deck.getItems().addAll(d.getNom());
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
