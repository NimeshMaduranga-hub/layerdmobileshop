package lk.ijse.layerdmobileshop.mobileshop.dto;

import java.math.BigDecimal;

public class OrderDetailDTO {

    // =========================
    // 🔴 DB CORE FIELDS
    // =========================
    private String orderId;
    private String itemCode;
    private int qty;
    private BigDecimal unitPrice;

    // =========================
    // 🔵 ITEM SNAPSHOT FIELDS (from Item table)
    // =========================
    private String description;
    private String storage;
    private String color;
    private String emiNo;
    private String warranty;

    // =========================
    // 🔵 UI ONLY FIELD
    // =========================
    private BigDecimal total;

    public OrderDetailDTO() {
    }

    // =========================
    // FOR DB SAVE (minimal)
    // =========================
    public OrderDetailDTO(String orderId, String itemCode, int qty, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    // =========================
    // FOR TABLEVIEW (FULL DISPLAY)
    // =========================
    public OrderDetailDTO(String orderId, String itemCode, String description,
                          int qty, BigDecimal unitPrice, BigDecimal total,
                          String storage, String color, String emiNo, String warranty) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
        this.storage = storage;
        this.color = color;
        this.emiNo = emiNo;
        this.warranty = warranty;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", description='" + description + '\'' +
                ", storage='" + storage + '\'' +
                ", color='" + color + '\'' +
                ", emiNo='" + emiNo + '\'' +
                ", warranty='" + warranty + '\'' +
                ", total=" + total +
                '}';
    }
}
