


package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Collector;
import lk.ijse.model.TM.collectorTM;
import lk.ijse.repositry.collectorRepo;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class collectorManageController  {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCollectorId;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<collectorTM> tblCollector;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCollectorId;

    @FXML
    private TextField txtCollectorName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSlry;


    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colCollectorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void loadAllCustomers() {
        ObservableList<collectorTM> obList = FXCollections.observableArrayList();

        try {
            List<Collector> customerList = collectorRepo.getAll();
            for (Collector collector : customerList) {
                collectorTM tm = new collectorTM(
                        collector.getCollectorId(),
                        collector.getName(),
                        collector.getAddress(),
                        collector.getContact(),
                        collector.getEmail(),
                        collector.getSalary()
                );

                obList.add(tm);
            }

            tblCollector.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCollectorId.getText();

        try {
            boolean isDeleted = collectorRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "collector deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
clearFields();
    }

    @FXML
    void btnSendEmailsOnAction(ActionEvent event) {

    }

    @FXML
    void btnSetOnAction(ActionEvent event) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtCollectorId.getText();
        String name = txtCollectorName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSlry.getText());

        Collector collector = new Collector(id, name, address, contact,email,salary);

        try {
            boolean isSaved = collectorRepo.save(collector);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "collector saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        clearFields();

    }
    private void clearFields() {
        txtCollectorId.setText("");
        txtCollectorName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtSlry.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtCollectorId.getText();
        String name = txtCollectorName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        double salary = Double.parseDouble(txtSlry.getText());

        Collector customer = new Collector(id, name, address, contact,email,salary);

        try {
            boolean isUpdated = collectorRepo.update(customer);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "collector updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
  clearFields();
    }





    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {

            String id = txtCollectorId.getText();

            Collector collector = collectorRepo.searchById(id);
            if (collector != null) {
                txtCollectorId.setText(collector.getCollectorId());
                txtCollectorName.setText(collector.getName());
                txtAddress.setText(collector.getAddress());
                txtContact.setText(collector.getContact());
                txtEmail.setText(collector.getEmail());
                txtSlry.setText(String.valueOf(collector.getSalary()));

            } else {
                new Alert(Alert.AlertType.INFORMATION, "collector not found!").show();
            }

    }

    public void collectorIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.HCID, txtCollectorId);
    }

    public void customerNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtCollectorName);
    }

    public void addressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtAddress);
    }

    public void emailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL, txtEmail);
    }

    public void salaryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtSlry);
    }
   public void contactOnKeyReleased(KeyEvent event) {
       Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtContact);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.HCID,txtCollectorId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtCollectorName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PRICE, txtSlry)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtContact)) return false;
        return true;
    }
}
