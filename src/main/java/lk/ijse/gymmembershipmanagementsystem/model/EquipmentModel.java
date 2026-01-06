package lk.ijse.gymmembershipmanagementsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.gymmembershipmanagementsystem.dto.EquipmentDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;

public class EquipmentModel {
    public boolean save(EquipmentDTO equipmentDTO) throws SQLException {   
             boolean result = CrudUtil.execute(
                     "INSERT INTO Equipments (name, qty, availability) VALUES (?, ?, ?)",
                     equipmentDTO.getName(), 
                     equipmentDTO.getQty(), 
                     equipmentDTO.getAvailability()
             );
             return result;
    }
    
    public boolean update(EquipmentDTO equipmentDTO) throws SQLException {
        
             boolean result = CrudUtil.execute("UPDATE Equipments SET name = ?, qty = ?, availability = ? WHERE equipmentsId = ?",
                     equipmentDTO.getName(), 
                     equipmentDTO.getQty(), 
                     equipmentDTO.getAvailability(),
                     equipmentDTO.getEquipmentsId()
                     );
             
             return result;
    }
    
    public boolean delete(String id) throws SQLException {
         
            boolean result = CrudUtil.execute("DELETE FROM Equipments WHERE equipmentsId = ?", id);
             
             return result;
    }
    
    public static EquipmentDTO search(String id) throws SQLException {
        
         ResultSet  result = CrudUtil.execute("SELECT * FROM Equipments WHERE equipmentsId = ?", id);
         
         if (result.next()) {
            int equipmentsID = result.getInt("equipmentsId");
            String name = result.getString("name");
            int  qty = result.getInt("qty");
            String availability = result.getString("availability");
            return new EquipmentDTO(equipmentsID, name, qty, availability);
        }
         return null;
    }
    
    public static List<EquipmentDTO> getAllEquipment() throws SQLException {
        
        List<EquipmentDTO> equipmentList = new ArrayList();
        
        ResultSet  result = CrudUtil.execute("SELECT * FROM Equipments ORDER BY equipmentsId DESC");
        
        while(result.next()) {
            int equipmentsID = result.getInt("equipmentsID");
            String name = result.getString("name");
            int qty = result.getInt("qty");
            String  availability = result.getString("availability");
            
            
            EquipmentDTO equipmentDTO = new EquipmentDTO(equipmentsID, name, qty, availability);
            
            equipmentList.add(equipmentDTO);
        }
        
        return equipmentList;
    }
}
