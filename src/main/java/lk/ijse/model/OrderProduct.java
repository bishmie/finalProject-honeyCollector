package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderProduct {

    private String productId;
    private String orderId;
    private int qty;
    private double sellingPrice;

}
