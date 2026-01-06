package lk.ijse.gymmembershipmanagementsystem.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.gymmembershipmanagementsystem.dto.TrainerDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;

public class TrainerModel {
    public boolean save(TrainerDTO trainerDTO) throws SQLException {   
             boolean result = CrudUtil.execute(
                     "INSERT INTO Trainer (name, age, nic, contact, email) VALUES (?,?, ?, ?, ?)",
                     trainerDTO.getName(), 
                     trainerDTO.getAge(), 
                     trainerDTO.getNic(), 
                     trainerDTO.getContact(), 
                     trainerDTO.getEmail()
             );
             return result;
    }
    
    public boolean update(TrainerDTO trainerDTO) throws SQLException {
        
             boolean result = CrudUtil.execute("UPDATE Trainer SET name = ?, age = ?, nic = ?, contact = ?, email =? WHERE trainerID = ?",
                     trainerDTO.getName(), 
                     trainerDTO.getAge(), 
                     trainerDTO.getNic(), 
                     trainerDTO.getContact(), 
                     trainerDTO.getEmail(),
                     trainerDTO.getTrainerId()
                     );
             
             return result;
    }
    
    public boolean delete(String id) throws SQLException {
         
            boolean result = CrudUtil.execute("DELETE FROM Trainer WHERE trainerID = ?", id);
             
             return result;
    }
    
    public TrainerDTO search(String id) throws SQLException {
        
         ResultSet  result = CrudUtil.execute("SELECT * FROM Trainer WHERE trainerID = ?", id);
         
         if (result.next()) {
            int trainerID = result.getInt("trainerID");
            String name = result.getString("name");
            int age = result.getInt("age");
            String nic = result.getString("nic");
            String contact = result.getString("contact");
            String email = result.getString("email");
            
            return new TrainerDTO(trainerID, name, age, nic, contact, email);
        }
         return null;
    }
    
    public List<TrainerDTO> getAllTrainer() throws SQLException {
        
        List<TrainerDTO> memberList = new ArrayList();
        
        ResultSet  result = CrudUtil.execute("SELECT * FROM Trainer ORDER BY trainerID DESC");
        
        while(result.next()) {
            int trainerID = result.getInt("trainerID");
            String name = result.getString("name");
            int age = result.getInt("age");
            String nic = result.getString("nic");
            String contact = result.getString("contact");
            String email = result.getString("email");
            
            TrainerDTO trainerDTO = new TrainerDTO(trainerID, name, age, nic, contact, email);
            
            memberList.add(trainerDTO);
        }
        return memberList;
    }
}