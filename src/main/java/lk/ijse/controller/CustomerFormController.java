package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.TM.CustomerTM;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
   @FXML
    private TextField txtCustomerId;
   @FXML
    private TextField txtCustomerName;
   @FXML
    private TextField txtCustomerAddress;
   @FXML
    private TextField txtCustomerContact;
   @FXML
    private TextField txtCustomerEmail;
   @FXML
    private TableView<CustomerTM> tblCustomerTable;
@FXML
    private AnchorPane rootNode;
@FXML
    private TableColumn<?,?> colCustomerId;
@FXML
    private TableColumn<?,?> colName;
@FXML
    private TableColumn<?,?> colAddress;
@FXML
    private TableColumn<?,?> colContact;
@FXML
    private TableColumn<?,?> colEmail;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }
    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTM tm = new CustomerTM(
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact(),
                        customer.getEmail()
                );
                obList.add(tm);
            }
            tblCustomerTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



  @FXML
     void btnSetOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();
        String email = txtCustomerEmail.getText();

        String sql = "INSERT INTO customer Values(?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setString(4,contact);
            pstm.setString(5,email);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerContact.setText("");
        txtCustomerEmail.setText("");
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtCustomerId.getText();

        String sql = "SELECT * FROM customer WHERE customerId =?";

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


                txtCustomerName.setText(name);
                txtCustomerAddress.setText(address);
                txtCustomerContact.setText(contact);
                txtCustomerEmail.setText(email);
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Customer ID Not Found!");
        }
    }
    @FXML
    void btnUpdateOnAction(ActionEvent actionEvent) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String CustomerId = txtCustomerId.getText();
        String Name = txtCustomerName.getText();
        String Address = txtCustomerAddress.getText();
        String Contact = txtCustomerContact.getText();
        String Email = txtCustomerEmail.getText();

        String sql = "UPDATE customer SET name =?, address =?, contact =?, email =? WHERE customerId =?";

        try {
            boolean isUpdate = CustomerRepo.update2(CustomerId, Name, Address, Contact, Email);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
     void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtCustomerId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();

    }

    public void btnDashBoardOnAction(ActionEvent actionEvent) throws IOException {
        navigateToDashBoard();

    }

    private void navigateToDashBoard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
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

    public void btnSendEmailsOnAction(ActionEvent actionEvent) throws IOException {
        navigateToEmailForm();
    }

    private void navigateToEmailForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/emailForm.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        rootNode.getChildren().clear();
        rootNode.getChildren().add(PerenetRootNode);


    }

    public void emailAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL, txtCustomerEmail);
    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtCustomerContact);
    }

    public void customerIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.CID, txtCustomerId);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.CID,txtCustomerId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtCustomerEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtCustomerContact)) return false;
        return true;
    }
}
