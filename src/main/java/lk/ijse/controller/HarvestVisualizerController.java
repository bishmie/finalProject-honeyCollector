/*package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class HarvestVisualizerController {

    public AnchorPane rootNode;
    public PieChart pieChart;

    public void initialize() {
        try {
            populatePieChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populatePieChart() throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();
        List<Double> harvestData = hiveVisualizeRepo.getHarvestData();

        for (Double liters : harvestData) {
            pieChart.getData().add(new PieChart.Data("Harvest", liters));
        }
    }

}*/
