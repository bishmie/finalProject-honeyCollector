package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.TM.OrderPlaceTM;

import java.io.IOException;

public class OrdersController {
    public AnchorPane rootNode;
    public TableView<OrderPlaceTM> tblOderPlacement;
    public TableColumn<?,?> colProductId;
    public TableColumn<?,?> colProductName;
    public TableColumn<?,?> colSellingPrice;
    public TableColumn<?,?> colqty;
    public TableColumn<?,?> colAction;

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        navigateToDashboard();

    }

    private void navigateToDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        navigateToLoginForm();

    }

    private void navigateToLoginForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    public void cmbProductOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }
}
