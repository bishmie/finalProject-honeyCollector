package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.repositry.BeeHiveRepo;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.InventoryRepo;
import lk.ijse.repositry.SupplierRepo;
import lk.ijse.util.Regex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public void initialize() {
        getBeeHiveIds();
    }

    private void getBeeHiveIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = BeeHiveRepo.getIds();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbBeeHiveId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSetOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
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
        clearFields();
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
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
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
        clearFields();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtInventoryId.getText();

        try {
            boolean isDeleted = InventoryRepo.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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

    public  void inventoryIdOnKeyReleased( ) {
        Regex.setTextColor(lk.ijse.util.TextField.IID, txtInventoryId);
    }

    public void typeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtType);
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtDescription);
    }

    public void qtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.QTY, txtQty);
    }

    public void unitPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtUnitPrice);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.IID,txtInventoryId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtType)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtDescription)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.QTY,txtQty)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PRICE,txtUnitPrice)) return false;

        return true;
    }
}

