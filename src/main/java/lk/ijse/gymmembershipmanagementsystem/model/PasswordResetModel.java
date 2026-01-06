package lk.ijse.gymmembershipmanagementsystem.model;

import java.sql.ResultSet;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;

public class PasswordResetModel {

    public void saveOTP(String email, String otp) throws Exception {
        CrudUtil.execute(
                "DELETE FROM password_reset WHERE email=?",
                email
        );

        CrudUtil.execute(
                "INSERT INTO password_reset (email, otp, created_at) VALUES (?, ?, NOW())",
                email,
                otp
        );
    }

    public boolean verifyOTP(String email, String otp) throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM password_reset WHERE email=? AND otp=?",
                email,
                otp
        );
        return rs.next();
    }

    public void clearOTP(String email) throws Exception {
        CrudUtil.execute(
                "DELETE FROM password_reset WHERE email=?",
                email
        );
    }
}
