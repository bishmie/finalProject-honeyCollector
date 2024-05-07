package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Order {
    private String orderId;
    private Date orderDate;
    private String qty;
    private String deliveryType;
    private String description;
    private  String customerId;
    private String paymentId;


    public Order(String orderId, String cusId, java.sql.Date date) {
    }
}