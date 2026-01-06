package lk.ijse.gymmembershipmanagementsystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gymmembershipmanagementsystem.db.DBConnection;
import lk.ijse.gymmembershipmanagementsystem.dto.MemberDTO;
import lk.ijse.gymmembershipmanagementsystem.dto.PaymentDTO;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    public boolean save(PaymentDTO paymentDTO) throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);

        try {
            boolean result = CrudUtil.execute(
                    "INSERT INTO Payment (memberID, date, amount) VALUES (?, ?, ?)",
                    paymentDTO.getMemberId(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getAmount()
            );

            if (result) {
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

    public boolean update(PaymentDTO paymentDTO) throws SQLException {
        boolean result = CrudUtil.execute(
                "UPDATE Payment SET memberID = ?, date = ?, amount = ? WHERE paymentID = ?",
                paymentDTO.getMemberId(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getAmount(),
                paymentDTO.getPaymentId()
        );
        return result;
    }

    public boolean delete(String id) throws SQLException {
        boolean result = CrudUtil.execute(
                "DELETE FROM Payment WHERE paymentID = ?",
                id
        );
        return result;
    }

    public PaymentDTO search(String id) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Payment WHERE paymentID = ?", id);

        if (rs.next()) {
            int paymentId = rs.getInt("paymentID");
            int memberId = rs.getInt("memberID");
            LocalDate date = rs.getDate("date").toLocalDate();
            double amount = rs.getDouble("amount");
            return new PaymentDTO(paymentId, memberId, amount, date);
        }
        return  null;
    }

    public List<PaymentDTO> getAllPayment() throws SQLException {

        List<PaymentDTO> paymentList = new ArrayList();
        ResultSet  result = CrudUtil.execute("SELECT * FROM Payment ORDER BY paymentID DESC");

        while(result.next()) {

            int payID = result.getInt("paymentID");
            int memberID = result.getInt("memberID");
            LocalDate date = result.getDate("date").toLocalDate();
            double amount = result.getDouble("amount");

            PaymentDTO paymentDTO = new PaymentDTO(payID, memberID, amount, date);

            paymentList.add(paymentDTO);

        }
        return paymentList;
    }

    public ObservableList<MemberDTO> loadMemberID()throws SQLException {
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
