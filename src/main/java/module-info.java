module lk.ijse.gymmembershipmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    requires net.sf.jasperreports.core;
    requires java.mail;

    opens lk.ijse.gymmembershipmanagementsystem to javafx.fxml;
    exports lk.ijse.gymmembershipmanagementsystem;
    opens lk.ijse.gymmembershipmanagementsystem.controller to javafx.fxml;
    exports lk.ijse.gymmembershipmanagementsystem.controller;
    opens lk.ijse.gymmembershipmanagementsystem.model to javafx.fxml;
    exports lk.ijse.gymmembershipmanagementsystem.model;
    opens lk.ijse.gymmembershipmanagementsystem.dto to javafx.base;
    exports lk.ijse.gymmembershipmanagementsystem.dto;
    opens lk.ijse.gymmembershipmanagementsystem.db to javafx.fxml;
    exports lk.ijse.gymmembershipmanagementsystem.db;
    opens lk.ijse.gymmembershipmanagementsystem.dto.tm to javafx.base;
    exports lk.ijse.gymmembershipmanagementsystem.dto.tm;
}
