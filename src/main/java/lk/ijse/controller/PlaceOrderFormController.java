package lk.ijse.controller;


import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.*;
import lk.ijse.model.TM.CartTM;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.PlaceOrderRepo;
import lk.ijse.repositry.productRepo;
import lk.ijse.repositry.OrderRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaceOrderFormController {


    public AnchorPane rootNode;
    public TableView<CartTM> tblPlaceOrder;
    public TableColumn<?, ?> colProductId;
    public TableColumn<?, ?> colProductName;
    public TableColumn<?, ?> colQty;
    public TableColumn<?, ?> colSellingPrice;
    public TableColumn<?, ?> colTotal;
    public TableColumn<?, ?> colAction;
    @FXML
    private ComboBox<String> cmbCustomerId;
    public TextField txtOrderId;
    public TextField txtQty;
    public TextField txtOrderDate;
    public ComboBox cmbProductId;
    public TextField txtSellingPrice;
    public TextField txtQntOnHand;
    public TextField txtProductName;
    public TextField txtCustomerName;
    public JFXButton btnAddToCart;
    public Label lblSubTotal;
    public Label lblCashReceived;
    public Label lblBalance;


    private ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void initialize() {
          setDate();
         getCurrentOrderId();
         getCustomerIds();
        getProductIds();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getProductIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = productRepo.getCodes();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbProductId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            txtOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtOrderDate.setText(String.valueOf(now));
    }

        public void btnAddToCartOnAction (ActionEvent actionEvent){
            String productId = (String) cmbProductId.getValue();
            String productName = txtProductName.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double sellingPrice = Double.parseDouble(txtSellingPrice.getText());
            double total = qty * sellingPrice;
            JFXButton btnRemove = new JFXButton("remove");
            btnRemove.setCursor(Cursor.HAND);

            btnRemove.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if(type.orElse(no) == yes) {
                    int selectedIndex = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                    obList.remove(selectedIndex);

                    tblPlaceOrder.refresh();
                    calculateNetTotal();
                }
            });

            for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                if(productId.equals(colProductId.getCellData(i))) {

                    CartTM tm = obList.get(i);
                    qty += tm.getQty();
                    total = qty * sellingPrice;

                    tm.setQty(qty);
                    tm.setTotal(total);

                    tblPlaceOrder.refresh();

                    calculateNetTotal();
                    return;
                }
            }

            CartTM tm = new CartTM(productId, productName, qty, sellingPrice, total, btnRemove);
            obList.add(tm);

            tblPlaceOrder.setItems(obList);
            calculateNetTotal();
            txtQty.setText("");
        }
    private void calculateNetTotal() {
        int SubTotal = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            SubTotal += (double) colTotal.getCellData(i);
        }
        lblSubTotal.setText(String.valueOf(SubTotal));
    }

        public void btnConfirmOrderOnAction (ActionEvent actionEvent){
            String orderId = txtOrderId.getText();
            String cusId = (String) cmbCustomerId.getValue();
            Date date = Date.valueOf(LocalDate.now());

            var order = new Order(orderId, cusId, date);

            List<OrderProduct> odList = new ArrayList<>();

            for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                CartTM tm = obList.get(i);

                OrderProduct od = new OrderProduct(
                        orderId,
                        tm.getProductId(),
                        tm.getQty(),
                        tm.getSellingPrice()
                );

                odList.add(od);
            }

            PlaceOrder po = new PlaceOrder(order, odList);
            try {
                boolean isPlaced = PlaceOrderRepo.placeOrder(po);
                if(isPlaced) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }


    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String id = cmbCustomerId.getValue();
        try {
            Customer customer = CustomerRepo.searchById(id);
            if(customer != null) {
                txtCustomerName.setText(customer.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cmbProductOnAction(ActionEvent actionEvent) {
        String code = (String) cmbProductId.getValue();

        try {
            Product product = productRepo.searchById(code);
            if(product != null) {
                txtProductName.setText(product.getProductName());
                txtSellingPrice.setText(product.getSellingPrice());
                txtQntOnHand.setText(product.getQty());
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtQtyOnAction(ActionEvent event) {btnAddToCartOnAction(event);

    }
}