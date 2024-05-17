package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.model.BeeHive;
import lk.ijse.model.Customer;
import lk.ijse.model.TM.CustomerTM;
import lk.ijse.model.TM.HiveTM;
import lk.ijse.repositry.BeeHiveRepo;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class HiveManageController {


    public TableView<HiveTM> tblHive;

    public AnchorPane rootNode;
    public TableColumn<?,?> colHiveId;
    public TableColumn<?,?> colType;
    public TableColumn<?,?> colLocation;
    public TableColumn<?,?> colInspectionDate;
    public TableColumn<?,?> colResult;

    public TableColumn<?,?> colPopulation;

    public TextField txtTYpe;
    public TextField txtLocation;
    public TextField txtPOpulation;
    public TextField txtInspectiondate;
    public TextField txtResults;
    public TextField txtHiveid;
    public ComboBox cmbQueenid;



    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }
    private void setCellValueFactory() {
        colHiveId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colPopulation.setCellValueFactory(new PropertyValueFactory<>("population"));
        colInspectionDate.setCellValueFactory(new PropertyValueFactory<>("inspectionDate"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("result"));
    }

    private void loadAllCustomers() {
        ObservableList<HiveTM> obList = FXCollections.observableArrayList();

        try {
            List<BeeHive> beeHiveList= BeeHiveRepo.getAll();
            for (BeeHive beeHive : beeHiveList) {
                HiveTM tm = new HiveTM(
                        beeHive.getBeehiveId(),
                        beeHive.getType(),
                        beeHive.getLocation(),
                        beeHive.getPopulation(),
                        beeHive.getInspectionDate(),
                        beeHive.getInspectionResult()

                );
                obList.add(tm);
            }
            tblHive.setItems(obList);
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

        String id = txtHiveid.getText();
        String type = txtTYpe.getText();
        String location = txtLocation.getText();
        String population = txtPOpulation.getText();
        String inspectionDate = txtInspectiondate.getText();
        String inspectionResult = txtResults.getText();



        String sql = "INSERT INTO beehive Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,type);
            pstm.setString(3,location);
            pstm.setString(4,population);
            pstm.setString(5,inspectionDate);
            pstm.setString(6,inspectionResult);



            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "hive is Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
    }

    private void clearFields() {
        txtHiveid.setText("");
        txtTYpe.setText("");
        txtLocation.setText("");
        txtPOpulation.setText("");
        txtInspectiondate.setText("");
        txtResults.setText("");

        //
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtHiveid.getText();
        String type = txtTYpe.getText();
        String location = txtLocation.getText();
        String population = txtPOpulation.getText();
        String inspectionDate = txtInspectiondate.getText();
        String inspectionResult = txtResults.getText();

        //

        String sql = "UPDATE beehive SET type =?, location =?, population =?, inspectionDate =?, inspectionResult =? WHERE beehiveId =?";

        try {
            boolean isUpdate = BeeHiveRepo.update2(id, type, location, population, inspectionDate,inspectionResult);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "hive Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "hive Not Updated").show();
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
        String id = txtHiveid.getText();

        try {
            boolean isDeleted = BeeHiveRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "hive is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();
    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtHiveid.getText();

        String sql = "SELECT * FROM beehive WHERE beehiveId = ?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString(2);
                String location = resultSet.getString(3);
                String population = resultSet.getString(4);
                String inspectionDate = resultSet.getString(5);
                String inspectionResult = resultSet.getString(6);



                txtTYpe.setText(type);
                txtLocation.setText(location);
                txtPOpulation.setText(population);
                txtInspectiondate.setText(inspectionDate);
                txtResults.setText(inspectionResult);


            } else {
                new Alert(Alert.AlertType.ERROR, "hive Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"hive ID Not Found!");
        }
    }


    public  void inspectionDateOnKeyReleased( ) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtInspectiondate);
    }

    public void hiveIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.HID, txtHiveid);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.HID,txtHiveid)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE,txtInspectiondate)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtTYpe)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtLocation)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtPOpulation)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtResults)) return false;

        return true;
    }

    public void typeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtTYpe);

    }

    public void locationOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtLocation);
    }

    public void populationOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtPOpulation);
    }

    public void resultOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtResults);
    }
}

