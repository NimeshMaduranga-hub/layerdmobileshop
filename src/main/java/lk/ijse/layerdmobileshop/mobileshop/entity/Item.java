package lk.ijse.layerdmobileshop.mobileshop.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Item {

    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;

    private String storage;
    private String color;
    private String emiNo;
    private String warranty;
    private LocalDate receivedDate;


    public Item() {}


    public Item(String code, String description, BigDecimal unitPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public Item(
            String code,
            String description,
            LocalDate receivedDate,
            int qtyOnHand,
            BigDecimal unitPrice,
            String storage,
            String color,
            String emiNo,
            String warranty
    ) {
        this.code = code;
        this.description = description;
        this.receivedDate = receivedDate;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.storage = storage;
        this.color = color;
        this.emiNo = emiNo;
        this.warranty = warranty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEmiNo() {
        return emiNo;
    }

    public void setEmiNo(String emiNo) {
        this.emiNo = emiNo;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", storage='" + storage + '\'' +
                ", color='" + color + '\'' +
                ", emiNo=" + emiNo +
                ", warranty='" + warranty + '\'' +
                ", receivedDate=" + receivedDate +
                '}';
    }
}
