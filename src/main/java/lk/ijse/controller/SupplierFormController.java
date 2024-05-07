package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.model.TM.SupplierTM;

import java.io.IOException;

public class SupplierFormController {
    public AnchorPane rootNode;
    public TextField txtSupplierId;
    public TextField txtSupplierName;
    public TextField txtSupplierAddress;
    public TextField txtSupplierContact;
    public TextField txtSupplierEmail;
    public TableView<SupplierTM> tblSupplier;
    public TableColumn<?,?> colSupplierId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colAddress;
    public TableColumn<?,?> colContact;
    public TableColumn<?,?> colEmail;

    public void btnSetOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        
    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        navigateToDashboard();
    }

    private void navigateToDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("dashboard Form");
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
}
