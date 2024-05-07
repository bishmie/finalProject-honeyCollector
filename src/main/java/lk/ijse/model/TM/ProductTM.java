package lk.ijse.model.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductTM {

      private String productId;
      private String  productName;
      private String sellingPrice;
      private String netWeight;
      private String qtyOnHand;
      private String qty;





      public ProductTM(String productId, String productName, String qtyOnHand) {
      }
}
