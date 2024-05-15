package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.model.BeeHive;
import lk.ijse.model.Customer;
import lk.ijse.model.TM.CustomerTM;
import lk.ijse.model.TM.HiveTM;
import lk.ijse.model.TM.QueenBeeTM;
import lk.ijse.model.beeQueen;
import lk.ijse.repositry.BeeHiveRepo;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.QueenBeeRepo;
import lk.ijse.repositry.productRepo;
import lk.ijse.util.Regex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueenBeeManageController {
    public AnchorPane rootNode;
    public TextField txtQueenId;
    public TextField txtBreedingHistory;
    public TextField txtBodyFeatures;
    public TextField txtHealthStatus;
    public TextField txtIntroDate;
    public TextField txtVariety;

    public TableColumn<?,?> colQueenBeeId;
    public TableColumn<?,?> colBeeHiveId;
    public TableColumn<?,?> colLocation;

    public TableColumn<?,?> colAvailableQueenId;
    public TableColumn<?,?> colAvailableVariety;
    public TableColumn<?,?> colAvailableBreedHis;
    public ComboBox cmbBeeHiveId;
    public TableView<QueenBeeTM> tblAssignedQueenBees;
    public TableView<QueenBeeTM> tblAvailableQueenBees;

    public void initialize() {
        getHiveIds();
        //setCellValueFactory();
        //loadAllAssignedQueenBees();
        setCellValueFactory2();
       loadAllAvailabledQueenBees();

    }

    private void getHiveIds() {
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


    /* private void setCellValueFactory() {
        colQueenBeeId.setCellValueFactory(new PropertyValueFactory<>("queenBeeId"));
        colBeeHiveId.setCellValueFactory(new PropertyValueFactory<>("beeHiveId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

}*/


    private void setCellValueFactory2() {
        colAvailableQueenId.setCellValueFactory(new PropertyValueFactory<>("queenId"));
        colAvailableVariety.setCellValueFactory(new PropertyValueFactory<>("variety"));
        colAvailableBreedHis.setCellValueFactory(new PropertyValueFactory<>("breedingHistory"));
        System.out.println("hi");
    }
     private void loadAllAvailabledQueenBees() {
         ObservableList<QueenBeeTM> obList = FXCollections.observableArrayList();

         try {
             List<beeQueen> beeQueenList = QueenBeeRepo.getAll();
             for (beeQueen bq : beeQueenList) {
                 QueenBeeTM tm = new QueenBeeTM(
                         bq.getQueenId(),
                         bq.getVariety(),
                         bq.getBreedingHistory()

                 );
                 obList.add(tm);
             }
             tblAvailableQueenBees.setItems(obList);
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

        String id = txtQueenId.getText();
        String breedingHistory = txtBreedingHistory.getText();
        String bodyFeatures = txtBodyFeatures.getText();
        String healthStatus = txtHealthStatus.getText();
        String introducedDate = txtIntroDate.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();
        String variety = txtVariety.getText();


        String sql = "INSERT INTO beequeen Values(?,?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,breedingHistory);
            pstm.setString(3,bodyFeatures);
            pstm.setString(4,healthStatus);
            pstm.setString(5,introducedDate);
            pstm.setString(6,beehiveId);
            pstm.setString(7,variety);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Queen bee is Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtQueenId.setText("");
        txtBreedingHistory.setText("");
        txtBodyFeatures.setText("");
        txtHealthStatus.setText("");
        txtIntroDate.setText("");
        cmbBeeHiveId.setValue("");
        txtVariety.setText("");

    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String queenbeeId = txtQueenId.getText();
        String breedingHistory = txtBreedingHistory.getText();
        String bodyFeatures = txtBodyFeatures.getText();
        String healthStatus = txtHealthStatus.getText();
        String introducedDate = txtIntroDate.getText();
        String beehiveId = (String) cmbBeeHiveId.getValue();
        String variety = txtVariety.getText();


        String sql = "UPDATE beequeen SET breedingHistory =?, bodyFeatures =?, healthStatus =?, introducedDate =?, beehiveId =?, variety =? WHERE queenId =?";

        try {
            boolean isUpdate = QueenBeeRepo.update2(queenbeeId, breedingHistory, bodyFeatures, healthStatus, introducedDate, beehiveId, variety);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Queen Bee Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Queen Bee Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtQueenId.getText();

        try {
            boolean isDeleted = QueenBeeRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "queen bee is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtQueenId.getText();

        String sql = "SELECT * FROM beequeen WHERE queenId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String breedingHistory = resultSet.getString(2);
                String bodyFeatures = resultSet.getString(3);
                String healthStatus = resultSet.getString(4);
                String introducedDate = resultSet.getString(5);
                String beehiveId = resultSet.getString(7);
                String variety = resultSet.getString(6);



                txtBreedingHistory.setText(breedingHistory);
                txtBodyFeatures.setText(bodyFeatures);
                txtHealthStatus.setText(healthStatus);
                txtIntroDate.setText(introducedDate);
                cmbBeeHiveId.setValue(beehiveId);
                txtVariety.setText(variety);


            } else {
                new Alert(Alert.AlertType.ERROR, "queen bee Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Queen bee ID Not Found!");
        }
    }

    public void cmbBeeHiveIdOnAction(ActionEvent actionEvent) {

    }

    public  void QueenIdOnKeyReleased(KeyEvent keyEvent ) {
        Regex.setTextColor(lk.ijse.util.TextField.QID, txtQueenId);
    }

    public void breedingHistoryOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtBreedingHistory);
    }

    public void bodyfeaturesOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtBodyFeatures);
    }

    public void healthStatusOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtHealthStatus);
    }

    public void introDateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtIntroDate);
    }

    public void varietyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtVariety);

    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.QID,txtQueenId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtBreedingHistory)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtBodyFeatures)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtHealthStatus)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtIntroDate)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtVariety)) return false;
        return true;
    }
}
