package lk.ijse.controller;


import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javafx.scene.layout.StackPane;


public class DashBoardController {

    public Label lblTotalCustomers;
    public Label lbltotalCus;
    public Label lblTotalSupliers;
    public Label lblTotalSup;
    public AnchorPane ChildRootNode;
    public AnchorPane ParentRootNode;
    public Pane paneId;
    public Label lblLocalDate;
    public BarChart graphAnalyze;
    public Label lblSetTime;
    public Label lblProduct;
    public StackPane root;
    public Label lblProductName;
    public Label lblTotalOrders;
    public Label lblTodayOrders;
    public Label lblTotRevenue;
    public Label lblTodayIncome;
    public VBox vboxDash;
    public VBox slider;
    public ImageView MenuBack;
    public Label Menu;

    @FXML
    private AnchorPane rootNode;
    private int customerCount;
    private int supplierCount;
    private int orderCount;
    private int todayOrderCount;
    private int totalRevenue;


    public void initialize() {
        setDate();
        setLocalTime();
        TotalCustomer();
        TotalSupplier();
        TotalOrders();
        TodayOrders();
        TotalRevenue();
        initializer();
        // ProductDashboard();
    }
    public void initializer(){
       slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event ->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(0);
            slide.play();
            slider.setTranslateX(-176);
            slide.setOnFinished((ActionEvent e) ->{
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });
        MenuBack.setOnMouseClicked(event ->{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);
            slide.setToX(0);
            slide.play();
            slider.setTranslateX(-176);
            slide.setOnFinished((ActionEvent e) ->{
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
    }

    private void TotalRevenue() {
        try {
            totalRevenue = getTotalRevenue();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setTotalRevenue(totalRevenue);

    }

    private void setTotalRevenue(int totalRevenue) {
        lblTotRevenue.setText(String.valueOf(totalRevenue));

    }

    private int getTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(qty * sellingPrice) AS totalRevenue FROM orderProduct;";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("totalRevenue");
        }

        return 0;

    }

    private void TodayOrders() {

        try {
            todayOrderCount = getTodayOrderCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setTodayOrderCount(todayOrderCount);
    }

    private void setTodayOrderCount(int todayOrderCount) {
        lblTodayOrders.setText(String.valueOf(todayOrderCount));

    }

    private int getTodayOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS todayOrderCount FROM orders WHERE DATE(orderDate) = CURRENT_DATE";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("todayOrderCount");
        }

        return 0;

    }


    private void TotalOrders() {
        try {
            orderCount = getOrderCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setOrderCount(orderCount);

    }

    private void setOrderCount(int orderCount) {
        lblTotalOrders.setText(String.valueOf(orderCount));

    }

    private int getOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS orderCount FROM orderProduct";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("orderCount");
        }

        return 0;

    }


    private void setLocalTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Define format
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    LocalTime timeInSriLanka = LocalTime.now(); // Get current time
                    String formattedTime = timeInSriLanka.format(formatter); // Format time
                    lblSetTime.setText(formattedTime); // Set formatted time to label
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline


    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblLocalDate.setText(String.valueOf(now));
    }

    private void TotalSupplier() {
        try {
            supplierCount = getSupplierCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(supplierCount);
    }

    private void setSupplierCount(int supplierCount) {
        lblTotalSup.setText(String.valueOf(supplierCount));

    }

    private int getSupplierCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS supplierrCount FROM supplier";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("supplierrCount");
        }

        return 0;

    }

    private void TotalCustomer() {
        try {
            customerCount = getCustomerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(customerCount);

    }


    private void setCustomerCount(int customerCount) {
        lbltotalCus.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customerCount FROM customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("customerCount");
        }

        return 0;
    }

    public void btnHiveManageOnAction(ActionEvent actionEvent) {
        try {
            navigateToHiveManagement();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToHiveManagement() throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/hiveManagement.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);

    }

    public void btnQueenBeeOnAction(ActionEvent actionEvent) throws IOException {
        navigateToQueenBeeManagement();

    }

    private void navigateToQueenBeeManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/queenBeeManage.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }

    public void btnHarvestManageOnAction(ActionEvent actionEvent) {
        navigateToHarvestManagement();
    }

    private void navigateToHarvestManagement() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/harvest.fxml"));
        Parent PerenetRootNode = null;

        try {
            PerenetRootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }


    public void btnProductManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToProductManage();
    }

    private void navigateToProductManage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProductForm.fxml"));
        Parent PerenetRootNode = null;

        try {
            PerenetRootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);

    }

    public void btnInventoryManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToInventory();

    }

    private void navigateToInventory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/InventoryManagement.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }

    public void btnSupplierManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToSupplierManagement();
    }

    private void navigateToSupplierManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SupplierManagement.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }

    public void btnEmployeeManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToBeekeeperManagement();
    }

    private void navigateToBeekeeperManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BeeKeeperManage.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);

    }

    public void btnCustomerManageOnAction(ActionEvent actionEvent) throws IOException {
        navigateToCustomerManagement();
    }

    private void navigateToCustomerManagement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/customerForm.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PlaceOrderForm.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }


    public void btnCollectorManageOnAction(ActionEvent actionEvent) {
        navigateToCollectorManage();
    }

    private void navigateToCollectorManage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/collector.fxml"));
        Parent PerenetRootNode = null;

        try {
            PerenetRootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }
}