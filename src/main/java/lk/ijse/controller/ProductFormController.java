package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;
import lk.ijse.model.TM.CustomerTM;
import lk.ijse.model.TM.ProductTM;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.productRepo;

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

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }
    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("Address"));

    }

    private void loadAllCustomers() {
        ObservableList<ProductTM> obList = FXCollections.observableArrayList();

        try {
            List<Product> productList = productRepo.getAll();
            for (Product product : productList) {
                ProductTM tm = new ProductTM(
                        product.getProductId(),
                        product.getProductName(),
                        product.getQtyOnHand()


                );
                obList.add(tm);
            }
            tblProduct.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void btnSetOnAction(ActionEvent actionEvent) {
        String ProductId = txtProductId.getText();
        String ProductName = txtProductName.getText();
        String SellingPrice = txtSellingPrice.getText();
        String NetWeight = txtNetWeight.getText();
        String Qty = txtQty.getText();

        String sql = "INSERT INTO product Values(?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,ProductId);
            pstm.setString(2,ProductName);
            pstm.setString(3,SellingPrice);
            pstm.setString(4,NetWeight);
            pstm.setString(5,Qty);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Product saved Successfully ").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtSellingPrice.setText("");
        txtNetWeight.setText("");
        txtQty.setText("");
    }



    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String ProductId = txtProductName.getText();
        String ProductName = txtProductName.getText();
        String SellingPrice = txtSellingPrice.getText();
        String NetWeight = txtNetWeight.getText();
        String Qty = txtQty.getText();

        String sql = "UPDATE product SET productName =?, sellingPrice =?, netWeight =?, qty =? WHERE productId =?";

        try {
            boolean isUpdate = productRepo.update2(ProductId, ProductName, SellingPrice, NetWeight, Qty);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Product is Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Product is Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtProductId.getText();

        try {
            boolean isDeleted = productRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "product is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
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


                txtProductName.setText(ProductName);
                txtSellingPrice.setText(SellingPrice);
                txtNetWeight.setText(NetWeight);
                txtQty.setText(Qty);
            } else {
                new Alert(Alert.AlertType.ERROR, "Product Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Product ID Not Found!");
        }
    }
    }

