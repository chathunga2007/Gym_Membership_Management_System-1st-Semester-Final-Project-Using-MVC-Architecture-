package lk.ijse.gymmembershipmanagementsystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gymmembershipmanagementsystem.db.DBConnection;
import lk.ijse.gymmembershipmanagementsystem.dto.MemberDTO;
import lk.ijse.gymmembershipmanagementsystem.dto.PaymentDTO;
import lk.ijse.gymmembershipmanagementsystem.dto.PrivateSessionDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrivateSessionModel {

    public boolean saveWithPayment(PrivateSessionDTO sessionDTO, PaymentDTO paymentDTO) throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);

        try {
            boolean sessionSaved = CrudUtil.execute(
                    "INSERT INTO Private_Session (memberID, timeIn, timeOut, extraCharges) VALUES (?, ?, ?, ?)",
                    sessionDTO.getMemberId(),
                    sessionDTO.getTimeIn(),
                    sessionDTO.getTimeOut(),
                    sessionDTO.getExtraCharges()
            );

            if (!sessionSaved) {
                con.rollback();
                return false;
            }

            boolean paymentSaved = CrudUtil.execute(
                    "INSERT INTO Payment (memberID, date, amount) VALUES (?, ?, ?)",
                    paymentDTO.getMemberId(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getAmount()
            );

            if (paymentSaved) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }

        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public boolean update(PrivateSessionDTO privateSessionDTO) throws SQLException {
        boolean result = CrudUtil.execute(
                "UPDATE Private_Session SET timeIn = ?, timeOut = ?, extraCharges = ?, memberID = ? WHERE privateSessionID = ?",
                privateSessionDTO.getTimeIn(),
                privateSessionDTO.getTimeOut(),
                privateSessionDTO.getExtraCharges(),
                privateSessionDTO.getMemberId(),
                privateSessionDTO.getPrivateSessionId()
        );
        return result;
    }

    public boolean delete(String privateSessionId) throws SQLException {
        boolean result = CrudUtil.execute("DELETE FROM Private_Session WHERE privateSessionID = ?", privateSessionId);
        return result;
    }

    public PrivateSessionDTO search(String id) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Private_Session WHERE privateSessionID = ?", id);

        if (rs.next()) {
            int privateSessionId = rs.getInt("privateSessionID");
            String timeIn = rs.getString("timeIn");
            String timeOut = rs.getString("timeOut");
            double extraCharges = rs.getDouble("extraCharges");
            int memberId = rs.getInt("memberID");
            return new PrivateSessionDTO(privateSessionId, memberId, timeIn, timeOut, extraCharges);
        }
        return  null;
    }

    public List<PrivateSessionDTO> getAllPrivateSession() throws SQLException {

        List<PrivateSessionDTO> privateSessionList = new ArrayList();
        ResultSet  result = CrudUtil.execute("SELECT ps.privateSessionID,\n" +
                "               ps.memberID,\n" +
                "               m.name AS memberName,\n" +
                "               ps.timeIn,\n" +
                "               ps.timeOut,\n" +
                "               ps.extraCharges\n" +
                "        FROM Private_Session ps\n" +
                "        JOIN Member m ON ps.memberID = m.memberID\n" +
                "        ORDER BY ps.privateSessionID DESC");

        while(result.next()) {

            int privateSessionId = result.getInt("privateSessionID");
            String timeIn = result.getString("timeIn");
            String timeOut = result.getString("timeOut");
            double extraCharges = result.getDouble("extraCharges");
            int memberId = result.getInt("memberID");
            String memberName = result.getString("memberName");

            PrivateSessionDTO privateSessionDTO = new PrivateSessionDTO(privateSessionId, memberId, timeIn, timeOut, extraCharges, memberName);

            privateSessionList.add(privateSessionDTO);

        }
        return privateSessionList;
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
}
