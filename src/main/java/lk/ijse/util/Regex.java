package lk.ijse.util;

import javafx.scene.control.TextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField, String text){
        String filed = "";
        switch (textField){
            case NAME:
                filed = "^[A-Za-z]+(?: [A-Za-z]+)*$";
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case ADDRESS:
                filed = "^([A-z0-9]|[-\\,.@+]|\\\\s){4,}$";
                break;
            case CONTACT:
                filed = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
                break;
            case PASSWORD:
                filed = "^(?=.[a-zA-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%?&]{8,}$";
               // filed = "^\\d{4}$";
                break;
            case CID:
                filed = "^C\\d{3,}$";
                break;
            case HID:
                filed = "^H\\d{3,}$";
                break;
            case BID:
                filed = "^B\\d{3,}$";
                break;
            case IID:
                filed = "^I\\d{3,}$";
                break;
            case SID:
                filed = "^S\\d{3,}$";
                break;
            case PROID:
                filed = "^P\\d+$";
                break;
            case OID:
                filed = "^O\\d{3,}$";
                break;
            case TID:
                filed = "^T\\d{3,}$";
                break;
            case QTY:
                filed = "^[1-9]\\d*$";
                break;
            case DATE:
                filed ="\\d{1,2}\\/\\d{1,2}\\/\\d{2,4}";
                break;
            case PRICE:
                filed ="^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case DESCRIPTION:
                filed = "^[A-Za-z0-9\\s!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$";
                break;
            case WEIGHT:
                filed = "^\\d+(\\.\\d+)?\\s*(ml|g|l)$";
                break;
            case QID:
                filed = "^Q\\d{3,}$";
                break;
            case HCID:
                filed = "^HC\\d{3,}$";
                break;
            case HAID:
                filed = "^HA\\d{3,}$";
                break;
            case  AMOUNT:
                filed = "^\\d+(\\.\\d+)?\\s?(ml|l)$";
                break;
            case GRADE:
                filed ="^[ABC]$";
                break;


        }
        Pattern pattern = Pattern.compile(filed);
        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return true;
        }
        Matcher matcher  = pattern.matcher(text);
        if (matcher.matches()){
            return true;
        }else {
            return  false;
        }
    }
    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-border-color: #00FF00;-fx-background-color: #e7dbc0;");
            return true;
        }else {
            textField.setStyle("-fx-border-color: red;-fx-border-radius: 5;-fx-border-width: 3;-fx-background-color: #e7dbc0;");
            return false;
        }
    }
    public static boolean setTextColorTxtArea(TextField location, TextArea textArea){
        if (Regex.isTextFieldValid(location, textArea.getText())){
            textArea.setStyle("-fx-border-color: #00FF00;-fx-background-color: #e7dbc0;");
            return true;
        }else {
            textArea.setStyle("-fx-border-color: red;-fx-border-radius: 5;-fx-border-width: 3;-fx-background-color: #e7dbc0;");
            return false;
        }
    }
}

