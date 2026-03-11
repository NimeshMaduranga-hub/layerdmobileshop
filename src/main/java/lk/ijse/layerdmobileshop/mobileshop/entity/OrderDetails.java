package lk.ijse.layerdmobileshop.mobileshop.entity;

import java.math.BigDecimal;

public class OrderDetails {

    private String OrderId;
    private String itemCode;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;

    private String code;
    private String description;

    public OrderDetails() {

    }


    public OrderDetails(String orderId, String itemCode, int qty, BigDecimal unitPrice) {
        OrderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetails(String Code, String description, int qty, BigDecimal unitPrice, BigDecimal total) {
        this.code=Code;
        this.description=description;
        this.qty=qty;
        this.unitPrice=unitPrice;
        this.total=total;
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

    @Override
    public String toString() {
        return "OrderDetails{" +
                "OrderId='" + OrderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }


}
