package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Product {
    private String productId;
    private String  productName;
    private String sellingPrice;
    private String netWeight;
    private String qtyOnHand;
    private String qty;


    public Product(String productId, String productName, String sellingPrice, String netWeight, String qty) {
    }

    public Product(String string, String string1) {
    }

    public Product(String string, String string1, String string2) {
    }
}

