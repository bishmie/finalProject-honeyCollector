package lk.ijse.model;

public class Harvest {
    private String harvestId;
   private String productionDate;
   private String qty;
    private String description;

    public String getHarvestId() {
        return harvestId;
    }

    public void setHarvestId(String harvestId) {
        this.harvestId = harvestId;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Harvest{" +
                "harvestId='" + harvestId + '\'' +
                ", productionDate='" + productionDate + '\'' +
                ", qty='" + qty + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
