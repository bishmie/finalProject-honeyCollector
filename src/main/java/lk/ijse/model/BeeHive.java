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
    private String queenId;



    public BeeHive(String queenId, String beehiveId, String location) {
    }
}

