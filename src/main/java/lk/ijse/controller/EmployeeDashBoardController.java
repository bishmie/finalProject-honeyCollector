package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EmployeeDashBoardController {
    public AnchorPane rootNode;
    public AnchorPane ChildRootNode;
    public Pane paneId;
    public Label lblLocalDate;
    public Label lblSetTime;
    public StackPane root;

    public Label lblTotalQueenBees;
    public Label lblTotalHives;
    private int queenbeeCount;
    private int hiveCount;

    public void initialize() {
        setDate();
        setLocalTime();
        totalHives();
        totalQueenBees();
    }

    private void totalQueenBees() {
        try {
            queenbeeCount = getQueeenCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setQueenCount(queenbeeCount);
    }

    private void setQueenCount(int queenbeeCount) {
        lblTotalQueenBees.setText(String.valueOf(hiveCount));

    }

    private int getQueeenCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS queenCount FROM beequeen";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("queenCount");
        }

        return 0;


    }

    private void totalHives() {
        try {
            hiveCount = getHiveCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setHiveCount(hiveCount);
    }

    private int getHiveCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS hiveCount FROM beehive";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("hiveCount");
        }

        return 0;

    }

    private void setHiveCount(int hiveCount) {
        lblTotalHives.setText(String.valueOf(hiveCount));
    }

    private void setLocalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Define format
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    LocalTime timeInSriLanka = LocalTime.now(); // Get current time
                    String formattedTime = timeInSriLanka.format(formatter); // Format time
                    lblSetTime.setText(formattedTime); // Set formatted time to label
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE); // Repeat indefinitely
        timeline.play(); // Start the timeline
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblLocalDate.setText(String.valueOf(now));
    }

    public void btnBeeKpeeperProfileOnAction(ActionEvent actionEvent) {
        try {
            navigateToBeekeeperProfile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToBeekeeperProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/BeekeeperProfile.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }

    public void btnCollectorOnAction(ActionEvent actionEvent) {
    }

    public void btnQueenBeeManagemetOnAction(ActionEvent actionEvent) {
        try {
            navigateToQueenBeeManagemet();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToQueenBeeManagemet() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/queenBeeManage.fxml"));
        Parent PerenetRootNode = null;

        PerenetRootNode = loader.load();


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }

    public void btnHiveManagemetOnAction(ActionEvent actionEvent) {
        navigateToHiveManagement();
    }

    private void navigateToHiveManagement() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/hiveManagement.fxml"));
        Parent PerenetRootNode = null;

        try {
            PerenetRootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        ChildRootNode.getChildren().clear();
        ChildRootNode.getChildren().add(PerenetRootNode);
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) {
        navigateToLoginPage();
    }

    private void navigateToLoginPage() {
        AnchorPane rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginPage.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    public void btnHarvestManagementOnAction(ActionEvent actionEvent) {
    }
}
