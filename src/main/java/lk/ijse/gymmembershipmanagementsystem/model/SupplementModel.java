package lk.ijse.gymmembershipmanagementsystem.model;

import lk.ijse.gymmembershipmanagementsystem.dto.SupplementDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplementModel {
    public boolean save(SupplementDTO supplementDTO) throws SQLException {
        boolean result = CrudUtil.execute("INSERT INTO supplement (id, name, qty, unit_price) VALUES (?,?,?, ?)", supplementDTO.getId(), supplementDTO.getName(), supplementDTO.getQty(), supplementDTO.getUnitPrice());

        return result;
    }

    public boolean update(SupplementDTO supplementDTO) throws SQLException {
        boolean result = CrudUtil.execute("UPDATE supplement SET name = ?, qty = ?, unit_price = ? WHERE id = ?", supplementDTO.getName(), supplementDTO.getQty(), supplementDTO.getId(), supplementDTO.getId());

        return result;
    }

    public boolean delete(String id) throws SQLException {
        boolean result = CrudUtil.execute("DELETE FROM supplement WHERE id = ?", id);

        return result;
    }

    public SupplementDTO search(String id) throws SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM supplement WHERE id = ?", id);

        if (result.next()) {
            int itemId = result.getInt("id");
            String itemName = result.getString("name");
            int itemQty = result.getInt("qty");
            double unitPrice = result.getDouble("unit_price");
            return new SupplementDTO(itemId, itemName, itemQty, unitPrice);
        }
        return null;
    }

    public List<SupplementDTO> getAllIds() throws SQLException {
        List<SupplementDTO> supplementDTOList = new ArrayList();

        ResultSet  rs = CrudUtil.execute("SELECT * FROM supplement ORDER BY id DESC");

        while(rs.next()) {
            int itemId = rs.getInt("id");
            String itemName = rs.getString("name");
            int itemQty = rs.getInt("qty");
            double unitPrice = rs.getDouble("unit_price");

            SupplementDTO supplementDTO = new SupplementDTO(itemId, itemName, itemQty, unitPrice);

            supplementDTOList.add(supplementDTO);
        }

        return supplementDTOList;
    }

    public boolean decreaseSupplementQty(int supplementId, int qty) throws SQLException {

        boolean isUpdated = CrudUtil.execute("UPDATE supplement SET qty=qty-? WHERE id=?",
                qty,
                supplementId);

        return isUpdated;
    }
}
