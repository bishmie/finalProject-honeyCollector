package lk.ijse.model.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class HiveTM {
    private String id;
    private String type;
    private String location;
    private String Population;
    private String inspectionDate;
    private String result;
    private String queenId;


    public HiveTM(String queenId, String beehiveId, String location) {
    }
}

