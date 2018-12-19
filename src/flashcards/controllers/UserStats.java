package flashcards.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserStats implements Initializable {
    @FXML private BarChart<String, Number> chart_cards_per_day;
    @FXML private Label lbl_cards_per_day_total;
    @FXML private Label lbl_cards_per_day_avg;
    @FXML private PieChart chart_cards_per_type;
    @FXML private Label lbl_cards_per_type_total;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //////Tests//////
        ArrayList<Pair<String, Integer>> cardNbPerDay = new ArrayList<Pair<String, Integer>>();
        ArrayList<Pair<String, Integer>> cardNbPerType = new ArrayList<Pair<String, Integer>>();

        cardNbPerDay.add(new Pair<>("J-7",15));
        cardNbPerDay.add(new Pair<>("J-6",12));
        cardNbPerDay.add(new Pair<>("J-5",16));
        cardNbPerDay.add(new Pair<>("J-4",28));
        cardNbPerDay.add(new Pair<>("J-3",5));
        cardNbPerDay.add(new Pair<>("J-2",12));
        cardNbPerDay.add(new Pair<>("J-1",13));
        cardNbPerDay.add(new Pair<>("J",4));

        cardNbPerType.add(new Pair<>("Non vu", 50));
        cardNbPerType.add(new Pair<>("En cours d'apprentissage", 168));
        cardNbPerType.add(new Pair<>("Aquis", 49));

        ///////Initialising cards per day graph///////////
        chart_cards_per_day.getXAxis().setLabel("Date");
        chart_cards_per_day.getYAxis().setLabel("Nombre de cartes");
        int total = 0;
        int moyenne;

        XYChart.Series series1 = new XYChart.Series();
        for (Pair<String, Integer> p: cardNbPerDay){
            total = total + p.getValue();
            series1.getData().add(new XYChart.Data(p.getKey(), p.getValue()));
        }
        moyenne = total/cardNbPerDay.size();

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


    }
}
