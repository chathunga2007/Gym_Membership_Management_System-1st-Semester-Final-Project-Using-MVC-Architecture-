package lk.ijse.gymmembershipmanagementsystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gymmembershipmanagementsystem.db.DBConnection;
import lk.ijse.gymmembershipmanagementsystem.dto.MemberDTO;
import lk.ijse.gymmembershipmanagementsystem.dto.MembershipDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;

public class MembershipModel {
    public boolean save(MembershipDTO membershipDTO) throws SQLException {
        boolean result = CrudUtil.execute(
                "INSERT INTO Membership (memberID, membershipType, issuedDate, expiryDate) VALUES (?,?,?,?)",
                membershipDTO.getMemberId(),
                membershipDTO.getMembershipType(),
                membershipDTO.getIssuedDate(),
                membershipDTO.getExpiryDate()
        );
        return result;
    }

    public boolean update(MembershipDTO membershipDTO) throws SQLException {
        boolean result = CrudUtil.execute(
                "UPDATE Membership SET memberID = ?, membershipType = ?, issuedDate = ?, expiryDate = ? WHERE membershipID = ?",
                membershipDTO.getMemberId(),
                membershipDTO.getMembershipType(),
                membershipDTO.getIssuedDate(),
                membershipDTO.getExpiryDate(),
                membershipDTO.getMembershipId()
        );
        return result;
    }

    public boolean delete(String id) throws SQLException {
        boolean result = CrudUtil.execute("DELETE FROM Membership WHERE membershipID = ?", id);

        return result;
    }

    public MembershipDTO search(String id) throws SQLException {

        ResultSet  result = CrudUtil.execute("SELECT * FROM Membership WHERE membershipID = ?", id);

        if (result.next()) {
            int membershipID = result.getInt("membershipID");
            String membershipType = result.getString("membershipType");
            LocalDate issuedDate = LocalDate.parse(result.getString("issuedDate"));
            LocalDate expiryDate = LocalDate.parse(result.getString("expiryDate"));
            int memID = result.getInt("memberID");
            return new MembershipDTO(membershipID, memID, membershipType, issuedDate, expiryDate);
        }
        return null;
    }

    public ObservableList<MemberDTO> loadMemberID()throws SQLException{
        ObservableList<MemberDTO> memberDTOS = FXCollections.observableArrayList();

        DBConnection dbc = DBConnection.getInstance();
        Connection conn = dbc.getConnection();

        String sql = "SELECT memberID , name FROM Member";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            memberDTOS.add(new MemberDTO(
                    rs.getInt("memberID"),
                    rs.getString("name")
            ));
        }
        return memberDTOS;
    }

    public List<MembershipDTO> getAllMembership() throws SQLException {

        List<MembershipDTO> membershipList = new ArrayList();

        ResultSet  result = CrudUtil.execute("SELECT \n" +
                "    mship.membershipID,\n" +
                "    mship.memberID,\n" +
                "    mem.name AS memberName,\n" +
                "    mship.membershipType,\n" +
                "    mship.issuedDate,\n" +
                "    mship.expiryDate\n" +
                "FROM \n" +
                "    Membership mship\n" +
                "JOIN \n" +
                "    Member mem ON mship.memberID = mem.memberID\n" +
                "ORDER BY \n" +
                "    mship.membershipID DESC;");

        while(result.next()) {
            int membershipID = result.getInt("membershipID");
            int memID = result.getInt("memberID");
            String membershipType = result.getString("membershipType");
            LocalDate issuedDate = LocalDate.parse(result.getString("issuedDate"));
            LocalDate expiryDate = LocalDate.parse(result.getString("expiryDate"));
            String memberName = result.getString("memberName");

            MembershipDTO membershipDTO = new MembershipDTO(membershipID, memID,  membershipType, issuedDate, expiryDate, memberName);

            membershipList.add(membershipDTO);
        }
        return membershipList;
    }
}
