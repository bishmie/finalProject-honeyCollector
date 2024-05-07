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
import lk.ijse.model.BeeHive;
import lk.ijse.model.Customer;
import lk.ijse.model.TM.CustomerTM;
import lk.ijse.model.TM.HiveTM;
import lk.ijse.model.TM.QueenBeeTM;
import lk.ijse.model.beeQueen;
import lk.ijse.repositry.BeeHiveRepo;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.QueenBeeRepo;

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
    public TableView<HiveTM> tblQueenBee;
    public TableColumn<?,?> colQueenBeeId;
    public TableColumn<?,?> colBeeHiveId;
    public TableColumn<?,?> colLocation;
    public TableView<QueenBeeTM> tblQueenBee1;
    public TableColumn<?,?> colAvailableQueenId;
    public TableColumn<?,?> colAvailableVariety;
    public TableColumn<?,?> colAvailableBreedHis;

    public void initialize() {
        setCellValueFactory();
        loadAllAssignedQueenBees();
        setCellValueFactory2();
       loadAllAvailabledQueenBees();
    }

       private void setCellValueFactory() {
        colQueenBeeId.setCellValueFactory(new PropertyValueFactory<>("queenBeeId"));
        colBeeHiveId.setCellValueFactory(new PropertyValueFactory<>("beeHiveId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

}
      private void loadAllAssignedQueenBees() {

          ObservableList<HiveTM> obList = FXCollections.observableArrayList();

          try {
              List<BeeHive> hiveList = BeeHiveRepo.getAll2();
              for (BeeHive beeHive : hiveList) {
                  HiveTM tm = new HiveTM(
                          beeHive.getQueenId(),
                          beeHive.getBeehiveId(),
                          beeHive.getLocation()

                  );
                  obList.add(tm);
              }
              tblQueenBee.setItems(obList);
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
}

    private void setCellValueFactory2() {
        colAvailableQueenId.setCellValueFactory(new PropertyValueFactory<>("queenBeeId"));
        colAvailableVariety.setCellValueFactory(new PropertyValueFactory<>("variety"));
        colAvailableBreedHis.setCellValueFactory(new PropertyValueFactory<>("breedingHistory"));

    }
     private void loadAllAvailabledQueenBees() {
         ObservableList<QueenBeeTM> obList = FXCollections.observableArrayList();

         try {
             List<beeQueen> beeQueenList = QueenBeeRepo.getAll();
             for (beeQueen bq : beeQueenList) {
                 QueenBeeTM tm = new QueenBeeTM(
                         bq.getQueenId(),
                         bq.getBreedingHistory(),
                         bq.getVariety()

                 );
                 obList.add(tm);
             }
             tblQueenBee1.setItems(obList);
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }

    public void btnSetOnAction(ActionEvent actionEvent) {
        String id = txtQueenId.getText();
        String breedingHistory = txtBreedingHistory.getText();
        String bodyFeatures = txtBodyFeatures.getText();
        String healthStatus = txtHealthStatus.getText();
        String introducedDate = txtIntroDate.getText();
        String variety = txtVariety.getText();

        String sql = "INSERT INTO beequeen Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,breedingHistory);
            pstm.setString(3,bodyFeatures);
            pstm.setString(4,healthStatus);
            pstm.setString(5,introducedDate);
            pstm.setString(6,variety);


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
        txtVariety.setText("");
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String queenbeeId = txtQueenId.getText();
        String breedingHistory = txtBreedingHistory.getText();
        String bodyFeatures = txtBodyFeatures.getText();
        String healthStatus = txtHealthStatus.getText();
        String introducedDate = txtIntroDate.getText();
        String variety = txtVariety.getText();

        String sql = "UPDATE beequeen SET breedingHistory =?, bodyFeatures =?, healthStatus =?, introducedDate =?, variety =? WHERE customerId =?";

        try {
            boolean isUpdate = QueenBeeRepo.update2(queenbeeId, breedingHistory, bodyFeatures, healthStatus, introducedDate,variety);
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
                String variety = resultSet.getString(6);


                txtBreedingHistory.setText(breedingHistory);
                txtBodyFeatures.setText(bodyFeatures);
                txtHealthStatus.setText(healthStatus);
                txtIntroDate.setText(introducedDate);
                txtVariety.setText(variety);
            } else {
                new Alert(Alert.AlertType.ERROR, "queen bee Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Queen bee ID Not Found!");
        }
    }
}
