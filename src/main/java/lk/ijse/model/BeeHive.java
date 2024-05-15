package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class BeeHive {
    private String beehiveId;
    private String type;
    private String location;
    private String population;
    private String inspectionDate;
    private String inspectionResult;


    public BeeHive(String string, String string1, String string2, String string3, String string4, String string5, String string6) {
    }

    public BeeHive(String location) {
    }
}

