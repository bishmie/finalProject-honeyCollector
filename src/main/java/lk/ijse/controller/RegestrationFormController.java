package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegestrationFormController {


    public TextField txtName;
    public TextField txtId;
    public TextField txtPassword;
    public TextField txtEmail;



    public void btnSignupOnAction(ActionEvent actionEvent) throws SQLException {
        String id = txtId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        boolean isSaved = saveUser(id, name, password,email);
        if(isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
        }

    }

    private boolean saveUser(String id, String name, String password, String email) throws SQLException {
        String sql = "INSERT INTO user VALUES(?, ?, ?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);
        pstm.setObject(2, name);
        pstm.setObject(3, password);
         pstm.setObject(4, email);

        return pstm.executeUpdate() > 0;
    }
}
