package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class beeQueen {
    private String queenId;
    private String breedingHistory;
    private String bodyFeatures;
    private String healthStatus;
    private String introducedDate;
    private String variety;



    public beeQueen(String queenId, String breedingHistory, String variety) {
    }
}