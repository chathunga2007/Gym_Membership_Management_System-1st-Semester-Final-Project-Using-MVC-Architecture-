package lk.ijse.gymmembershipmanagementsystem.dto;

public class OrderSupplementDTO {
    private int orderSupplementId;
    private int supplementId;
    private int qty;
    private double price;

    public OrderSupplementDTO() {
    }

    public OrderSupplementDTO(int supplementId, int qty, double price) {
        this.supplementId = supplementId;
        this.qty = qty;
        this.price = price;
    }

    public OrderSupplementDTO(int orderSupplementId, int supplementId, int qty, double price) {
        this.orderSupplementId = orderSupplementId;
        this.supplementId = supplementId;
        this.qty = qty;
        this.price = price;
    }

    public int getOrderSupplementId() {
        return orderSupplementId;
    }

    public void setOrderSupplementId(int orderSupplementId) {
        this.orderSupplementId = orderSupplementId;
    }

    public int getSupplementId() {
        return supplementId;
    }

    public void setSupplementId(int supplementId) {
        this.supplementId = supplementId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderSupplementDTO{" +
                "orderSupplementId=" + orderSupplementId +
                ", supplementId=" + supplementId +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
