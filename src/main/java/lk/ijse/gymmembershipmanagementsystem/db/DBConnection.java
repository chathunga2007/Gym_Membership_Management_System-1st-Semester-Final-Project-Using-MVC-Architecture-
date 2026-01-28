package lk.ijse.gymmembershipmanagementsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String DB_URL = "jdbc:mysql://localhost:3306/Gym_Membership_Management_System";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "";
    
    private Connection conn = null;
    private static DBConnection dbc;

    public DBConnection() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbc==null) {
            dbc = new DBConnection();
        }
        return dbc;
    }

    public Connection getConnection() {
        return conn;
    }
}
