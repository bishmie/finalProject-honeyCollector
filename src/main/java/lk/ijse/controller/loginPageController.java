package lk.ijse.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.util.Regex;


import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginPageController {

    public AnchorPane rootNode;
    public TextField txtUserId;
    public PasswordField txtPassword;
    public Pane videoPane;



   /* public void initialize(URL location, ResourceBundle resources) {
        File file = new File("/icon/loginVideo.mp4");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        mediaView.setMediaPlayer(mp);
        mp.play();



    }*/


    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException {

        String userId = txtUserId.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT id, password FROM user WHERE id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if(pw.equals(dbPw)) {
                if (userId.equals("bishmi") && pw.equals("1234")) {


                    navigateToTheDashboard();
                }
                else {
                    navigateToDashBoard2();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
    }

    private void navigateToTheDashboard()  {
        AnchorPane rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    private void navigateToDashBoard2(){
        AnchorPane rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/Employeedashboard.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");

    }

    public void btnSignUpOnAction( )  {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/regestrationForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");

        stage.show();
    }


}

