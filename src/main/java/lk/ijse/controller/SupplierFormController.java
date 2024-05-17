package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.model.TM.SupplierTM;
import lk.ijse.repositry.BeeHiveRepo;
import lk.ijse.repositry.InventoryRepo;
import lk.ijse.repositry.SupplierRepo;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    public AnchorPane rootNode;
    public TextField txtSupplierId;
    public TextField txtSupplierName;
    public ComboBox cmbInventoryId;
    public TextField txtSupplierAddress;
    public TextField txtContact;
    public TextField txtEmail;

    public void initialize() {
        getInventoryIds();
    }

    private void getInventoryIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = InventoryRepo.getIds();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbInventoryId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnTaskSearchOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();

        String sql = "SELECT * FROM supplier WHERE supplierId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String email = resultSet.getString(5);
                String inventoryId= resultSet.getString(6);


                txtSupplierId.setText(id);
                txtSupplierName.setText(name);
                txtSupplierAddress.setText(address);
                txtContact.setText(contact);
                txtEmail.setText(email);
                cmbInventoryId.setValue(inventoryId);

            } else {
                new Alert(Alert.AlertType.ERROR, "supplier Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"supplier ID Not Found!");
        }

    }


    public void btnSetSupplierOnAction(ActionEvent actionEvent) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String inventoryId = (String) cmbInventoryId.getValue();

        String sql = "INSERT INTO supplier Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setString(4, contact);
            pstm.setString(5, email);
            pstm.setString(6, inventoryId);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearSupplierFields();
    }
        private void clearSupplierFields() {
            txtSupplierId.setText("");
            txtSupplierName.setText("");
            txtSupplierAddress.setText("");
            txtContact.setText("");
            txtEmail.setText("");
            cmbInventoryId.setValue("");
        }


    public void btnUpdateSupplierOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String supplierId = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String inventoryId= (String) cmbInventoryId.getValue();

        String sql = "UPDATE supplier SET name =?, address =?, contact =?, email =?, inventoryId =? WHERE supplierId =?";

        try {
            boolean isUpdate = SupplierRepo.update2(supplierId, name, address, contact, email, inventoryId);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier does Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearSupplierFields();

    }

    public void btnDeleteSupplierOnAction(ActionEvent actionEvent) {
        String id = txtSupplierId.getText();

        try {
            boolean isDeleted = SupplierRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearSupplierFields();
    }



    public void supplierOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.SID, txtSupplierId);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.SID,txtSupplierId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtSupplierName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtSupplierAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return false;
        return true;
    }

    public void supNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtSupplierName);
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtSupplierAddress);
    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtContact);
    }

    public void emailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL, txtEmail);
    }
}