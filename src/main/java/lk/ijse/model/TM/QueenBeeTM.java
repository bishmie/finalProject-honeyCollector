package lk.ijse.model.TM;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueenBeeTM {
    private String queenId;
    private String breedingHistory;
    private String bodyFeatures;
    private String healthStatus;
    private String introducedDate;
    private String variety;

    public QueenBeeTM(String queenId, String breedingHistory, String variety) {
    }
}
