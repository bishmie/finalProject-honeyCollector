package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegestrationFormController {


    public TextField txtName;
    public TextField txtPassword;
    public AnchorPane rootNode;
    public TextField txtUserId;
    public TextField txtEmail;


    public void btnSignupOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if (!isValid()) {
            // If validation fails, show an error alert and return early
            new Alert(Alert.AlertType.ERROR, "Please ensure all fields are correctly filled out.").show();
            return;
        }
        String id = txtUserId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();


        boolean isSaved = saveUser(id, name, password,email);
        if(isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "You are successfully saved! as a user").show();
        }
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/Employeedashboard.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();

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

    public void userIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtUserId);
    }

    public void userNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME, txtName);
    }

    public void passwordOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD, txtPassword);
    }

    public void emailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL, txtEmail);
    }
    public boolean isValid(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtUserId)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return false;

        return true;
    }
}
