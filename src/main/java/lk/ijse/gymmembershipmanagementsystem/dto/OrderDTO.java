package lk.ijse.gymmembershipmanagementsystem.dto;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private int orderId;
    private int memberId;
    private Date orderDate;
    List<OrderSupplementDTO> orderSupplements;

    public OrderDTO() {
    }

    public OrderDTO(int memberId, Date orderDate, List<OrderSupplementDTO> orderSupplements) {
        this.memberId = memberId;
        this.orderDate = orderDate;
        this.orderSupplements = orderSupplements;
    }

    public OrderDTO(int orderId, int memberId, Date orderDate, List<OrderSupplementDTO> orderSupplements) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.orderDate = orderDate;
        this.orderSupplements = orderSupplements;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderSupplementDTO> getOrderSupplements() {
        return orderSupplements;
    }

    public void setOrderSupplements(List<OrderSupplementDTO> orderSupplements) {
        this.orderSupplements = orderSupplements;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", memberId=" + memberId +
                ", orderDate=" + orderDate +
                ", orderSupplements=" + orderSupplements +
                '}';
    }
}
