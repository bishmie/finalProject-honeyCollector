package lk.ijse.model.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class OrderPlaceTM {
    private String orderId;
    private String orderDate;
    private int qty;
    private String deliveryType;
    private String description;


}
