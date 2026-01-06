package lk.ijse.gymmembershipmanagementsystem.model;

import lk.ijse.gymmembershipmanagementsystem.dto.OrderSupplementDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;
import java.util.List;

public class OrderSupplementModel {
    private SupplementModel supplementModel = new SupplementModel();

    public boolean saveOrderSupplement(int orderId, List<OrderSupplementDTO> orderSupplementList) throws Exception {

        for (OrderSupplementDTO orderSupplementDTO : orderSupplementList) {
            if(CrudUtil.execute(
                    "INSERT INTO order_items (order_id, supplement_id, qty, price) VALUES (?,?,?,?)",
                    orderId,
                    orderSupplementDTO.getSupplementId(),
                    orderSupplementDTO.getQty(),
                    orderSupplementDTO.getPrice())) {

                if(!supplementModel.decreaseSupplementQty(orderSupplementDTO.getSupplementId(), orderSupplementDTO.getQty())) {
                    throw new Exception("Something went wrong when decreasing qty");
                }

            } else {
                throw new Exception("Something went wrong when inserting data into order items");
            }
        }
        return true;
    }
}
