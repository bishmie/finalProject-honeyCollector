package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class beeQueen {
    private String queenId;
    private String breedingHistory;
    private String bodyFeatures;
    private String healthStatus;
    private String introducedDate;
    private String beehiveId;
    private String variety;


    public beeQueen(String queenId, String breedingHistory, String bodyFeatures, String healthStatus, String introducedDate, String beehiveId, String variety) {
        this.queenId = queenId;
        this.breedingHistory = breedingHistory;
        this.bodyFeatures = bodyFeatures;
        this.healthStatus = healthStatus;
        this.introducedDate = introducedDate;
        this.beehiveId = beehiveId;
        this.variety = variety;
    }

    public beeQueen(String string, String string1, String string2) {
    }

    public String getQueenId() {
        return queenId;
    }

    public void setQueenId(String queenId) {
        this.queenId = queenId;
    }

    public String getBreedingHistory() {
        return breedingHistory;
    }

    public void setBreedingHistory(String breedingHistory) {
        this.breedingHistory = breedingHistory;
    }

    public String getBodyFeatures() {
        return bodyFeatures;
    }

    public void setBodyFeatures(String bodyFeatures) {
        this.bodyFeatures = bodyFeatures;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getIntroducedDate() {
        return introducedDate;
    }

    public void setIntroducedDate(String introducedDate) {
        this.introducedDate = introducedDate;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getBeehiveId() {
        return beehiveId;
    }

    public void setBeehiveId(String beehiveId) {
        this.beehiveId = beehiveId;
    }

    @Override
    public String toString() {
        return "beeQueen{" +
                "queenId='" + queenId + '\'' +
                ", breedingHistory='" + breedingHistory + '\'' +
                ", bodyFeatures='" + bodyFeatures + '\'' +
                ", healthStatus='" + healthStatus + '\'' +
                ", introducedDate='" + introducedDate + '\'' +
                ", variety='" + variety + '\'' +
                ", beehiveId='" + beehiveId + '\'' +
                '}';
    }
}