package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegestrationFormController {


    public TextField txtName;
    public TextField txtPassword;
    public AnchorPane rootNode;
    public TextField txtUserId;


    public void btnSignupOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        String id = txtUserId.getText();
        String name = txtName.getText();
        String password = txtPassword.getText();

        boolean isSaved = saveUser(id, name, password);
        if(isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
        }
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();

    }

    private boolean saveUser(String id, String name, String password) throws SQLException {
        String sql = "INSERT INTO user VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);
        pstm.setObject(2, name);
        pstm.setObject(3, password);


        return pstm.executeUpdate() > 0;
    }
}
