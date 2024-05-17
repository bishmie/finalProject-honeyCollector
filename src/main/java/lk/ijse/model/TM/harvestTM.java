package lk.ijse.model.TM;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class harvestTM {
    private String harvestId;
    private String productionDate;
    private String amountOfLiters;
    private String qualityNotes;
    private String harvestType;
    private String grade;
}
