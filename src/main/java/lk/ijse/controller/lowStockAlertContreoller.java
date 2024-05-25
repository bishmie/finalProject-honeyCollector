package lk.ijse.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.awt.*;

public class lowStockAlertContreoller {

    @FXML
    private Label lblProductId;

    @FXML
    private AnchorPane rootNode;

    public void setProductId(String productId) {
        lblProductId.setText("Low stock for product: " + productId);
    }

}