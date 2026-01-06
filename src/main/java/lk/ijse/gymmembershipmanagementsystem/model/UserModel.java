package lk.ijse.gymmembershipmanagementsystem.model;

import java.sql.ResultSet;
import lk.ijse.gymmembershipmanagementsystem.util.CrudUtil;

public class UserModel {

    public boolean checkLogin(String username, String password) throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM user WHERE userName=? AND password=?",
                username,
                password
        );
        return rs.next();
    }

    public boolean emailExists(String email) throws Exception {
        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM user WHERE email=?",
                email
        );
        return rs.next();
    }

    public boolean updatePassword(String email, String newPassword) throws Exception {
        return CrudUtil.execute(
                "UPDATE user SET password=? WHERE email=?",
                newPassword,
                email
        );
    }
}
