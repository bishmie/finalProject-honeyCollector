package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Harvest;
import lk.ijse.model.TM.harvestTM;
import lk.ijse.repositry.BeeHiveRepo;
import lk.ijse.repositry.HarvestRepo;
import lk.ijse.repositry.collectorRepo;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class HarvestDetailController {



    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colGrade;

    @FXML
    private TableColumn<?, ?> colHarvestId;

    @FXML
    private TableColumn<?, ?> colHarvestType;

    @FXML
    private TableColumn<?, ?> colProductionDate;

    @FXML
    private TableColumn<?, ?> colQualityNote;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<harvestTM> tblHarvest;

    @FXML
    private TextField txtAmountOfLiters;

    @FXML
    private TextField txtGrade;

    @FXML
    private TextField txtHarvestId;

    @FXML
    private TextField txtHarvestType;

    @FXML
    private TextField txtProductionDate;

    @FXML
    private TextField txtQualityNotes;

    @FXML
    private ComboBox<String> cmbBeehiveId;

    @FXML
    private ComboBox<String> cmbCollectorId;

    public void initialize() {
        getBeehiveIds();
        getCollectorIds();
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colHarvestId.setCellValueFactory(new PropertyValueFactory<>("harvestId"));
        colProductionDate.setCellValueFactory(new PropertyValueFactory<>("productionDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amountOfLiters"));
        colQualityNote.setCellValueFactory(new PropertyValueFactory<>("QualityNotes"));
        colHarvestType.setCellValueFactory(new PropertyValueFactory<>("HarvestType"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    private void loadAllCustomers() {
        ObservableList<harvestTM> obList = FXCollections.observableArrayList();

        try {
            List<Harvest> customerList = HarvestRepo.getAll();
            for (Harvest harvest : customerList) {
                harvestTM tm = new harvestTM(
                        harvest.getHarvestId(),
                        harvest.getProductionDate(),
                        harvest.getAmountOfLiters(),
                        harvest.getQualityNotes(),
                        harvest.getHarvestType(),
                        harvest.getGrade()
                );

                obList.add(tm);
            }

            tblHarvest.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCollectorIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = collectorRepo.getIds();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbCollectorId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getBeehiveIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = BeeHiveRepo.getIds();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbBeehiveId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtHarvestId.getText();

        try {
            boolean isDeleted = HarvestRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "harvest successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();

    }

    @FXML
    void btnSetOnAction(ActionEvent event) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtHarvestId.getText();
        String date = txtProductionDate.getText();
        String amount = txtAmountOfLiters.getText();
        String qualityNotes = txtQualityNotes.getText();
        String beehiveId = (String) cmbBeehiveId.getValue();
        String collectorId = (String) cmbCollectorId.getValue();
        String harvestType = txtHarvestType.getText();
        String grade = txtGrade.getText();


        Harvest harvest = new Harvest(id, date, amount,qualityNotes,beehiveId,collectorId,harvestType,grade);

        try {
            boolean isSaved = HarvestRepo.save(harvest);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, " New Harvest Successfully Added!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        clearFields();

    }

    private void clearFields() {

            txtHarvestId.setText("");
            txtProductionDate.setText("");
            txtAmountOfLiters.setText("");
            txtQualityNotes.setText("");
        cmbBeehiveId.setValue(null);
        cmbCollectorId.setValue(null);
        txtHarvestType.setText("");
            txtGrade.setText("");

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }

        String id = txtHarvestId.getText();
        String date = txtProductionDate.getText();
        String amount = txtAmountOfLiters.getText();
        String qualityNotes = txtQualityNotes.getText();
        String beehiveId = (String) cmbBeehiveId.getValue();
        String collectorId = (String) cmbCollectorId.getValue();
        String harvestType = txtHarvestType.getText();
        String grade = txtGrade.getText();

        Harvest harvest = new Harvest(id, date, amount,qualityNotes,beehiveId,collectorId,harvestType,grade);

        try {
            boolean isUpdated = HarvestRepo.update(harvest);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "harvest updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearFields();

    }



    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtHarvestId.getText();

        Harvest harvest = HarvestRepo.searchById(id);
        if (harvest != null) {
            txtHarvestId.setText(harvest.getHarvestId());
            txtProductionDate.setText(harvest.getProductionDate());
            txtAmountOfLiters.setText(harvest.getAmountOfLiters());
            txtQualityNotes.setText(harvest.getQualityNotes());
            cmbBeehiveId.setValue(harvest.getBeehiveId());
            cmbCollectorId.setValue(harvest.getCollectorId());
            txtHarvestType.setText(harvest.getHarvestType());
            txtGrade.setText(harvest.getGrade());

        } else {
            new Alert(Alert.AlertType.INFORMATION, "harvest not found!").show();
        }

    }

    public void productionDateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE, txtProductionDate);
    }

    public void harvestIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.HAID, txtHarvestId);
    }

    public void amountOfqtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.AMOUNT, txtAmountOfLiters);
    }

    public void harvestTypeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtHarvestType);
    }

    public void qualityNotesOnKeyRelleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION, txtQualityNotes);
    }

    public void gradeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.GRADE, txtGrade);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.HAID,txtHarvestId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE,txtProductionDate)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtAmountOfLiters)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtQualityNotes)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DESCRIPTION,txtHarvestType)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.GRADE,txtGrade)) return false;
        return true;
    }
}
