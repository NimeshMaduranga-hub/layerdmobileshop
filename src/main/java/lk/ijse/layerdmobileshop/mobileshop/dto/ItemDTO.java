package lk.ijse.layerdmobileshop.mobileshop.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemDTO {

    private String code;
    private String description;
    private LocalDate receivedDate;

    private int qtyOnHand;
    private BigDecimal unitPrice;

    private String storage;
    private String color;
    private String emiNo;
    private String warranty;

    public ItemDTO() {
    }


    public ItemDTO(String code, String description, BigDecimal unitPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }
    public ItemDTO(
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

    public ItemDTO(String code, String description, BigDecimal unitPrice, int qtyOnHand, String storage, String color, String emiNo, String warranty) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.storage = storage;
        this.color = color;
        this.emiNo = emiNo;
        this.warranty = warranty;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getReceivedDate() { return receivedDate; }
    public void setReceivedDate(LocalDate receivedDate) { this.receivedDate = receivedDate; }

    public int getQtyOnHand() { return qtyOnHand; }
    public void setQtyOnHand(int qtyOnHand) { this.qtyOnHand = qtyOnHand; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getEmiNo() { return emiNo; }
    public void setEmiNo(String emiNo) { this.emiNo = emiNo; }

    public String getWarranty() { return warranty; }
    public void setWarranty(String warranty) { this.warranty = warranty; }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", receivedDate=" + receivedDate +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", storage='" + storage + '\'' +
                ", color='" + color + '\'' +
                ", emiNo=" + emiNo +
                ", warranty='" + warranty + '\'' +
                '}';
    }
}