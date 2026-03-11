package lk.ijse.layerdmobileshop.mobileshop.dto;

import java.math.BigDecimal;

public class OrderDetailDTO {

    private String OrderId;
    private String itemCode;
    private String Code;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private String description;



    public OrderDetailDTO(){

    }

    public OrderDetailDTO(String itemCode, int qty, BigDecimal unitPrice,BigDecimal total) {
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public OrderDetailDTO(String orderId, String itemCode, int qty, BigDecimal unitPrice) {
        OrderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetailDTO(String itemCode, String description, int qty, BigDecimal unitPrice, BigDecimal total) {
        this.itemCode=itemCode;
        this.description=description;
        this.qty=qty;
        this.unitPrice=unitPrice;
        this.total=total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "OrderId='" + OrderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", Code='" + Code + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                ", description='" + description + '\'' +
                '}';
    }
}
