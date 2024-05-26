package lk.ijse.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.chart.PieChart;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;



public class HarvestVisualizerController implements Initializable {

    public AnchorPane rootNode;
    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("H001", 2200),
                        new PieChart.Data("H002", 1000),
                        new PieChart.Data("H003", 1500),
                        new PieChart.Data("H004", 3000));


        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " amount: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);



        pieChart.setDepthTest(DepthTest.ENABLE);
        pieChart.setStartAngle(90); // Adjust start angle for a 3D-like effect

    }
}