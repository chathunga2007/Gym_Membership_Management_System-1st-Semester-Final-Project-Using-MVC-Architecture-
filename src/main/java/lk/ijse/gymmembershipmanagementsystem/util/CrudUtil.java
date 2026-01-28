package lk.ijse.gymmembershipmanagementsystem.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.gymmembershipmanagementsystem.db.DBConnection;

public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ptsm = conn.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            ptsm.setObject(i + 1, obj[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            ResultSet results = ptsm.executeQuery();
            return (T) results;
        } else {
            int result = ptsm.executeUpdate();
            boolean results = result > 0;
            return (T) (Boolean) results;
        }
    }
}