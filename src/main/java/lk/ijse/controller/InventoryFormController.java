package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.InventoryRepo;
import lk.ijse.repositry.SupplierRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryFormController {


    public AnchorPane rootNode;
    public TextField txtInventoryId;
    public TextField txtType;
    public TextField txtDescription;
    public TextField txtQty;
    public TextField txtUnitPrice;
    public TextField txtSupplierId;
    public TextField txtSupplierName;
    public ComboBox cmbInventoryId;
    public TextField txtSupplierAddress;
    public ComboBox cmbBeeHiveId;
    public TextField txtContact;
    public TextField txtEmail;

    public void btnSetOnAction(ActionEvent actionEvent) {
        String id = txtInventoryId.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUnitPrice.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();

        String sql = "INSERT INTO inventory Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, id);
            pstm.setString(2, type);
            pstm.setString(3, description);
            pstm.setString(4, qty);
            pstm.setString(5, unitPrice);
            pstm.setString(6, beehiveId);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtInventoryId.setText("");
        txtType.setText("");
        txtDescription.setText("");
        txtQty.setText("");
        txtUnitPrice.setText("");
        cmbBeeHiveId.setValue("");
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String inventoryId = txtInventoryId.getText();
        String type = txtType.getText();
        String description = txtDescription.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUnitPrice.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();

        String sql = "UPDATE inventory SET type =?, description =?, qty =?, unitPrice =?, beehiveId =? WHERE inventoryId =?";

        try {
            boolean isUpdate = InventoryRepo.update2(inventoryId, type, description, qty, unitPrice, beehiveId);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "item does Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtInventoryId.getText();

        try {
            boolean isDeleted = InventoryRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtInventoryId.getText();

        String sql = "SELECT * FROM inventory WHERE inventoryId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString(2);
                String description = resultSet.getString(3);
                String qty = resultSet.getString(4);
                String unitPrice = resultSet.getString(5);
                String beeHiveId = resultSet.getString(6);


                txtInventoryId.setText(id);
                txtType.setText(type);
                txtDescription.setText(description);
                txtQty.setText(qty);
                txtUnitPrice.setText(unitPrice);
                cmbBeeHiveId.setValue(beeHiveId);

            } else {
                new Alert(Alert.AlertType.ERROR, "Item Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION, "Item ID Not Found!");
        }
    }
}

