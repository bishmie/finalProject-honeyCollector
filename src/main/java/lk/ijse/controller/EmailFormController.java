package lk.ijse.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;
import lk.ijse.repositry.CustomerRepo;
import lk.ijse.repositry.productRepo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailFormController {

    public AnchorPane rootNode;
    public JFXTextField txtEmail;

    public TextArea txtAreaMassege;
    public ComboBox cmbCustomerName;

    public void initialize() throws SQLException {
        getCustomerIds();
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        String receipientEmail = txtEmail.getText();
        try {
            sendEmail(receipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private void sendEmail(String receipientEmail) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String myAccountEmail = "bishmimalshi@gmail.com";
        String password = "mlyg cpgb vczp apai";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message massage = prepareMessage(session, myAccountEmail, receipientEmail, txtAreaMassege.getText());
        if (massage != null) {
            new Alert(Alert.AlertType.INFORMATION, "Send Successfully").show();

        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong. Try Again").show();
        }
        Transport.send(massage);
    }

    private Message prepareMessage(Session session, String myAccountEmail, String receipientEmail, String msg) {

        try {
            Message message;
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(receipientEmail)

            });
            message.setSubject("Messeges");
            message.setText(msg);
            return message;
        }catch (Exception e) {
            Logger.getLogger(EmailFormController.class.getName()).log(Level.SEVERE,null, e);
        }
        return null;
    }
    private void getCustomerIds() throws SQLException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbCustomerName.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String id = (String) cmbCustomerName.getValue();
        try {
            Customer customer = CustomerRepo.searchById(id);
            if(customer != null) {
                txtEmail.setText(customer.getEmail());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

