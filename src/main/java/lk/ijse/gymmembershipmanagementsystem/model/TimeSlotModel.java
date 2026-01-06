package lk.ijse.gymmembershipmanagementsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.gymmembershipmanagementsystem.dto.TimeSlotDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;

public class TimeSlotModel {
    
    public boolean save(TimeSlotDTO slotDTO) throws SQLException {
        boolean result = CrudUtil.execute(
                     "INSERT INTO Time_Slot(date, timeIn, timeOut) VALUES (?,?, ?)",
                     slotDTO.getDate(),
                     slotDTO.getTimeIn(), 
                     slotDTO.getTimeOut()
         );
        return result;
    }
    
    public boolean update(TimeSlotDTO slotDTO) throws SQLException {
        boolean result = CrudUtil.execute(
                "UPDATE Time_Slot SET date = ?, timeIn = ?, timeOut = ? WHERE slotID = ?",
                slotDTO.getDate(),
                slotDTO.getTimeIn(),
                slotDTO.getTimeOut(),
                slotDTO.getSlotId()
          );
        return result;
    }
    
    public boolean delete(String id) throws SQLException {
        boolean result = CrudUtil.execute("DELETE FROM Time_Slot WHERE slotID = ?", id);
        return result;
    }
    
    public TimeSlotDTO search(String id) throws SQLException {
        ResultSet  result = CrudUtil.execute("SELECT * FROM Time_Slot WHERE slotID = ?", id);
        if (result.next()) {
            int slotID = result.getInt("slotID");
            LocalDate date = LocalDate.parse(result.getString("date"));
            String  timeIn = result.getString("timeIn");
            String timeOut = result.getString("timeOut");
            return new TimeSlotDTO(slotID, date, timeIn, timeOut);
        }
         return null;
    }
    
    public List<TimeSlotDTO> getAllTimeSlot() throws SQLException {
        
        List<TimeSlotDTO> slotList = new ArrayList();
        
        ResultSet  result = CrudUtil.execute("SELECT * FROM Time_Slot ORDER BY slotID DESC");
        
        while(result.next()) {
            int slotID = result.getInt("slotID");
            LocalDate date = LocalDate.parse(result.getString("date"));
            String  timeIn = result.getString("timeIn");
            String timeOut = result.getString("timeOut");
            
            TimeSlotDTO slotDTO = new TimeSlotDTO(slotID, date, timeIn, timeOut);
            
            slotList.add(slotDTO);
        }
        return slotList;
    }
}
