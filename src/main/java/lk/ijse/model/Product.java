package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class Product {
    private String productId;
    private String  productName;
    private String sellingPrice;
    private String netWeight;
   // private String qtyOnHand;
    private String qty;

    public Product(String productId, String productName, String sellingPrice, String netWeight, String qty) {
        this.productId = productId;
        this.productName = productName;
        this.sellingPrice = sellingPrice;
        this.netWeight = netWeight;
        this.qty = qty;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", netWeight='" + netWeight + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}

