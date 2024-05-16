package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private String productId;
    private String productName;
    private String sellingPrice;
    private String netWeight;
    private String qty;
    private String harvestId;

}