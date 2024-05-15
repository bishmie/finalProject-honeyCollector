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
    public TextField txtCashReceived;
    public Label lblOrderId;
    public Label lblOderDate;
    public Label lblSellingPrice;
    public Label lblQtyOnHand;
    public Label lblCustomerName;
    public Label lblProductName;
    @FXML
    private ComboBox<String> cmbCustomerId;

    public TextField txtQty;

    public ComboBox<String> cmbProductId;



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
            lblOrderId.setText(nextOrderId);

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
        lblOderDate.setText(String.valueOf(now));
    }

        public void btnAddToCartOnAction (ActionEvent actionEvent) {


                String code = cmbProductId.getValue();
                String description = lblProductName.getText();
                int qty = Integer.parseInt(txtQty.getText());
                double unitPrice = Double.parseDouble(lblSellingPrice.getText());
                double total = qty * unitPrice;
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
                    if(code.equals(colProductId.getCellData(i))) {

                        CartTM tm = obList.get(i);
                        qty += tm.getQty();
                        total = qty * unitPrice;

                        tm.setQty(qty);
                        tm.setTotal(total);

                        tblPlaceOrder.refresh();

                        calculateNetTotal();
                        return;
                    }
                }

                CartTM tm = new CartTM(code, description, qty, unitPrice, total, btnRemove);
                obList.add(tm);

                tblPlaceOrder.setItems(obList);
                calculateNetTotal();
                txtQty.setText("");
            }

    private int calculateNetTotal() {
        int SubTotal = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            SubTotal += (double) colTotal.getCellData(i);
        }
        lblSubTotal.setText(String.valueOf(SubTotal));
        //btnMoneyReceiving();
        return SubTotal;
    }



    private void calculateBalance() {
        int netTotal = calculateNetTotal();
        int receivedAmount = Integer.parseInt(txtCashReceived.getText());
        int balance = receivedAmount - netTotal;
        lblBalance.setText(String.valueOf(balance));

    }


        public void btnConfirmOrderOnAction (ActionEvent actionEvent){

                String orderId = lblOrderId.getText();
                String cusId = cmbCustomerId.getValue();
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
                lblCustomerName.setText(customer.getName());
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
                lblProductName.setText(product.getProductName());
                lblSellingPrice.setText(product.getSellingPrice());
                lblQtyOnHand.setText(product.getQty());
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtQtyOnAction(ActionEvent event) {btnAddToCartOnAction(event);

    }

    public void txtCashReceivedOnAction(ActionEvent actionEvent) {
    }

    public void btnMoneyReceiving() {
        calculateBalance();

    }
}