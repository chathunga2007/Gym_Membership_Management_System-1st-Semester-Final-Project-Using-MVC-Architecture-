package lk.ijse.gymmembershipmanagementsystem.model;

import lk.ijse.gymmembershipmanagementsystem.db.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashBoardModel {

    public static int getTotalMembers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Member WHERE status='ACTIVE'";
        ResultSet rs = DBConnection.getInstance()
                .getConnection()
                .createStatement()
                .executeQuery(sql);

        return rs.next() ? rs.getInt(1) : 0;
    }

    public static int getTotalTrainers() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Trainer";
        ResultSet rs = DBConnection.getInstance()
                .getConnection()
                .createStatement()
                .executeQuery(sql);

        return rs.next() ? rs.getInt(1) : 0;
    }

    public static int getTodaySessions() throws SQLException {
        String sql = """
            SELECT COUNT(*)
            FROM Session s
            JOIN Time_Slot t ON s.slotID = t.slotID
            WHERE t.date = DATE(CONVERT_TZ(NOW(), 'SYSTEM', '+05:30'))
        """;

        ResultSet rs = DBConnection.getInstance()
                .getConnection()
                .createStatement()
                .executeQuery(sql);

        return rs.next() ? rs.getInt(1) : 0;
    }

    public static double getTodayIncome() throws SQLException {
        double paymentIncome = 0;
        double supplementIncome = 0;

        ResultSet rs1 = DBConnection.getInstance()
                .getConnection()
                .createStatement()
                .executeQuery(
                        "SELECT IFNULL(SUM(amount),0) FROM Payment WHERE date = DATE(CONVERT_TZ(NOW(), 'SYSTEM', '+05:30'))"
                );
        if (rs1.next()) paymentIncome = rs1.getDouble(1);

        ResultSet rs2 = DBConnection.getInstance()
                .getConnection()
                .createStatement()
                .executeQuery("""
                    SELECT IFNULL(SUM(price * qty),0)
                    FROM order_items oi
                    JOIN orders o ON oi.order_id = o.id
                    WHERE o.date = DATE(CONVERT_TZ(NOW(), 'SYSTEM', '+05:30'))
                """);
        if (rs2.next()) supplementIncome = rs2.getDouble(1);

        return paymentIncome + supplementIncome;
    }
}
