package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BeeKeeperProfileController {
    public AnchorPane rootNode;
    public TextField txtId;
    public Label lblTaskName;
    public Label lblDescription;
    public Label lblDueTime;
    public Label lblBeekeeperName;


    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        String sql = "SELECT b.name, t.taskName, t.dueDate, t.description " +
                "FROM beekeeper b " +
                "INNER JOIN task t ON b.beeKeeperId = t.beeKeeperId " +
                "WHERE t.beeKeeperId = ?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String taskName = resultSet.getString("taskName");
                Date dueDate = resultSet.getDate("dueDate");
                String description = resultSet.getString("description");

                lblBeekeeperName.setText(name);
                lblTaskName.setText(taskName);
                lblDueTime.setText(dueDate.toString());
                lblDescription.setText(description);
            } else {
                new Alert(Alert.AlertType.ERROR, "Beekeeper Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION, "Error fetching beekeeper details!").show();
        }
    }
}
