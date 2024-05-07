package lk.ijse.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {
    @FXML
    private AnchorPane rootNode;

    public void btnHiveManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToHiveManagement();
    }

    private void navigateToHiveManagement() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/hiveManagement.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("HiveManage Form");
    }

    public void btnQueenBeeOnAction(ActionEvent actionEvent) throws IOException {
        navigateToQueenBeeManagement();

    }

    private void navigateToQueenBeeManagement() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/queenbeeManage.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("queenbee Form");
    }

    public void btnHarvestManageOnAction(ActionEvent actionEvent) {
    }

    public void btnProductManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToProductManage();
    }

    private void navigateToProductManage() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/productForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("ProductManage Form");
    }

    public void btnInventoryManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToInventory();

    }

    private void navigateToInventory() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/InventoryManagement.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Supplier Form");
    }

    public void btnSupplierManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToSupplierManagement();
    }

    private void navigateToSupplierManagement() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/SupplierManagement.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Supplier Form");

    }

    public void btnEmployeeManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToBeekeeperManagement();
    }

    private void navigateToBeekeeperManagement() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/BeeKeeperManage.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("beekeeper Form");

    }

    public void btnCustomerManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToCustomerManagement();
    }

    private void navigateToCustomerManagement() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/customerForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Customer Form");

    }

    public void btnTotalPurchasesOnAction(ActionEvent actionEvent) {
    }

    public void btnTotalSalesOnAction(ActionEvent actionEvent) {
    }

    public void btnToatalCustomerOnAction(ActionEvent actionEvent) {
    }

    public void btnTotalSupplierOnAction(ActionEvent actionEvent) {
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        navigateToLoginPage();
    }
    private void navigateToLoginPage() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    public void btnOrderPlaceOnAction(ActionEvent actionEvent) throws IOException {
        navigateToOrderForm();

    }

    private void navigateToOrderForm() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Order Form");
    }


}

