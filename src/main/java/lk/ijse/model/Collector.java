package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Collector {
    private String collectorId;
    private String name;
    private String address;

    private String contact;
    private String email;
    private double salary;

}