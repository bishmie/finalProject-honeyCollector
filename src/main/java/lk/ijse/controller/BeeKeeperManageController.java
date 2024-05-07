package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;
import lk.ijse.repositry.BeeKeeperManageRepo;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.TaskRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BeeKeeperManageController {
    public AnchorPane rootNode;
    public TextField txtBeeKeeperId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtSalary;
    public TextField txtTaskId;
    public TextField txtTaskName;
    public DatePicker dpDueDate;
    public TextArea txtAreaDescription;
    public ComboBox cmbBeeKeeperId;
    public TextField txtDescription;

    public void btnSetOnAction(ActionEvent actionEvent) {
        String id = txtBeeKeeperId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String salary = txtSalary.getText();

        String sql = "INSERT INTO beekeeper Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setString(4,contact);
            pstm.setString(5,email);
            pstm.setString(6,salary);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Beekeeper is Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtBeeKeeperId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
    }



    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String beekeeperId = txtBeeKeeperId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Contact = txtContact.getText();
        String Email = txtEmail.getText();
        String Salary = txtSalary.getText();

        String sql = "UPDATE beekeeper SET name =?, address =?, contact =?, email =?, salary =? WHERE beekeeperId =?";

        try {
            boolean isUpdate = BeeKeeperManageRepo.update2(beekeeperId, Name, Address, Contact, Email, Salary);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "beekeeper is Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "beekeeper Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtBeeKeeperId.getText();

        try {
            boolean isDeleted = BeeKeeperManageRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "beekeeper is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnAssignTaskOnAction(ActionEvent actionEvent) {
        String id = txtTaskId.getText();
        String name = txtTaskName.getText();
        String description = txtDescription.getText();
        String date = String.valueOf(dpDueDate.getValue());
        String beekeeper = (String) cmbBeeKeeperId.getValue();

        String sql = "INSERT INTO task Values(?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,description);
            pstm.setString(4,date);
            pstm.setString(5,beekeeper);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "task Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    public void btnUpdateTaskOnAction(ActionEvent actionEvent) {
        String taskId = txtTaskId.getText();
        String Name = txtTaskName.getText();
        String description = txtDescription.getText();
        String date = String.valueOf(dpDueDate.getValue());
        String beekeeperId = (String) cmbBeeKeeperId.getValue();


        String sql = "UPDATE task SET taskName =?, description =?, dueDate =?, beekeeperId =? WHERE taskId =?";

        try {
            boolean isUpdate = TaskRepo.update2(taskId, Name, description, date, beekeeperId);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "task is Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "task Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    public void btnDeleteTaskOnAction(ActionEvent actionEvent) {
        String id = txtTaskId.getText();

        try {
            boolean isDeleted = TaskRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "task is successfully deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearTaskOnAction(ActionEvent actionEvent) {
        clearTaskFields();
    }

    private void clearTaskFields() {
        txtTaskId.setText("");
        txtTaskName.setText("");
        txtDescription.setText("");
        dpDueDate.setValue(LocalDate.parse("null"));
        cmbBeeKeeperId.setValue(null);

    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtBeeKeeperId.getText();

        String sql = "SELECT * FROM beekeeper WHERE beekeeperId =?";

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
                String salary = resultSet.getString(6);


                txtName.setText(name);
                txtAddress.setText(address);
                txtContact.setText(contact);
                txtEmail.setText(email);
                txtSalary.setText(salary);

            } else {
                new Alert(Alert.AlertType.ERROR, "Beekeeper Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Beekeeper ID Not Found!");
        }
    }

    public void btnTaskSearchOnAction(ActionEvent actionEvent) {
        String id = txtTaskId.getText();

        String sql = "SELECT * FROM task WHERE taskId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                String date = resultSet.getString(4);
                String beekeeperId = resultSet.getString(5);



                txtTaskName.setText(name);
                txtDescription.setText(description);
                dpDueDate.setValue(LocalDate.parse(date));
                cmbBeeKeeperId.setValue(beekeeperId);


            } else {
                new Alert(Alert.AlertType.ERROR, "task Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"task ID Not Found!");
        }
    }
}

