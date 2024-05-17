package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;
import lk.ijse.model.TM.CustomerTM;
import lk.ijse.model.TM.ProductTM;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.QueenBeeRepo;
import lk.ijse.repositry.productRepo;
import lk.ijse.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductFormController {
    public AnchorPane rootNode;
    public TableView<ProductTM> tblHive;
    public TableColumn<?,?> colProductId;
    public TableColumn<?,?> colProductName;
    public TableColumn<?,?> colQtyOnHand;
    public TextField txtProductId;
    public TextField txtNetWeight;
    public TextField txtProductName;
    public TextField txtQty;
    public TextField txtSellingPrice;
    public TableView<ProductTM> tblProduct;
    public ComboBox cmbHarvestId;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        getHarvestIds();

    }

    private void getHarvestIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = productRepo.getCodes();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbHarvestId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }

    private void loadAllCustomers() {
        ObservableList<ProductTM> obList = FXCollections.observableArrayList();

        try {
            List<Product> productList = productRepo.getAll();
            for (Product product : productList) {
                ProductTM tm = new ProductTM(
                        product.getProductId(),
                        product.getProductName(),
                        product.getQty()


                );
                obList.add(tm);
            }
            tblProduct.setItems(obList);
            tblProduct.setVisible(true);
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

        String ProductId = txtProductId.getText();
        String ProductName = txtProductName.getText();
        String SellingPrice = txtSellingPrice.getText();
        String NetWeight = txtNetWeight.getText();
        String Qty = txtQty.getText();
        String harvestId = (String) cmbHarvestId.getValue();

        String sql = "INSERT INTO product Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,ProductId);
            pstm.setString(2,ProductName);
            pstm.setString(3,SellingPrice);
            pstm.setString(4,NetWeight);
            pstm.setString(5,Qty);
            pstm.setString(6,harvestId);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Product saved Successfully ").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
    }

    private void clearFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtSellingPrice.setText("");
        txtNetWeight.setText("");
        txtQty.setText("");
        cmbHarvestId.setValue(null);
    }



    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String ProductId = txtProductId.getText();
        String ProductName = txtProductName.getText();
        String SellingPrice = txtSellingPrice.getText();
        String NetWeight = txtNetWeight.getText();
        String Qty = txtQty.getText();
        String harvestId = (String) cmbHarvestId.getValue();

        String sql = "UPDATE product SET productName =?, sellingPrice =?, netWeight =?, qty =?, harvestId =? WHERE productId =?";

        try {
            boolean isUpdate = productRepo.update2(ProductId, ProductName, SellingPrice, NetWeight, Qty, harvestId);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Product is Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Product is Not Updated").show();
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

        String id = txtProductId.getText();

        try {
            boolean isDeleted = productRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "product is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
    }



    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtProductId.getText();

        String sql = "SELECT * FROM product WHERE productId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String ProductName = resultSet.getString(2);
                String SellingPrice = resultSet.getString(3);
                String NetWeight = resultSet.getString(4);
                String Qty = resultSet.getString(5);
                String harvestId = resultSet.getString(6);


                txtProductName.setText(ProductName);
                txtSellingPrice.setText(SellingPrice);
                txtNetWeight.setText(NetWeight);
                txtQty.setText(Qty);
                cmbHarvestId.setValue(harvestId);

            } else {
                new Alert(Alert.AlertType.ERROR, "Product Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Product ID Not Found!");
        }
    }

    public void txtProductIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PROID, txtProductId);
    }

    public void netWeightOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.WEIGHT, txtNetWeight);
    }

    public void productNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtProductName);
    }

    public void qtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.QTY, txtQty);
    }

    public void sellingPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtSellingPrice);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.PROID,txtProductId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtProductName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PRICE,txtSellingPrice)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.WEIGHT,txtNetWeight)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.QTY,txtQty)) return false;
        return true;
    }


    public void btnHarvestIdOnAction(ActionEvent actionEvent) {

    }

    public void btnPrintOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/ProductReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}

