package lk.ijse.gymmembershipmanagementsystem.dto.tm;

public class OrderSupplementTM {
    private int supplementId;
    private String supplementName;
    private double unitPrice;
    private int supplementQty;
    private double supplementTotal;

    public OrderSupplementTM() {
    }

    public OrderSupplementTM(String supplementName, double unitPrice, int supplementQty, double supplementTotal) {
        this.supplementName = supplementName;
        this.unitPrice = unitPrice;
        this.supplementQty = supplementQty;
        this.supplementTotal = supplementTotal;
    }

    public OrderSupplementTM(int supplementId, String supplementName, double unitPrice, int supplementQty, double supplementTotal) {
        this.supplementId = supplementId;
        this.supplementName = supplementName;
        this.unitPrice = unitPrice;
        this.supplementQty = supplementQty;
        this.supplementTotal = supplementTotal;
    }

    public int getSupplementId() {
        return supplementId;
    }

    public void setSupplementId(int supplementId) {
        this.supplementId = supplementId;
    }

    public String getSupplementName() {
        return supplementName;
    }

    public void setSupplementName(String supplementName) {
        this.supplementName = supplementName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getSupplementQty() {
        return supplementQty;
    }

    public void setSupplementQty(int supplementQty) {
        this.supplementQty = supplementQty;
    }

    public double getSupplementTotal() {
        return supplementTotal;
    }

    public void setSupplementTotal(double supplementTotal) {
        this.supplementTotal = supplementTotal;
    }

    @Override
    public String toString() {
        return "OrderSupplementTM{" +
                "supplementId=" + supplementId +
                ", supplementName='" + supplementName + '\'' +
                ", unitPrice=" + unitPrice +
                ", supplementQty=" + supplementQty +
                ", supplementTotal=" + supplementTotal +
                '}';
    }
}
