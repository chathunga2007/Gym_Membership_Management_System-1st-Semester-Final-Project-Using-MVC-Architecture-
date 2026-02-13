package lk.ijse.gymmembershipmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gymmembershipmanagementsystem.App;
import java.io.IOException;

public class ForgotPasswordController {
    @FXML
    private TextField emailField;

    public static String email;

    private final PasswordResetController pWord = new PasswordResetController();

    @FXML
    void sendOTP() {
        try {
            email = emailField.getText();
            pWord.sendOTP(email);
            App.setRoot("VerifyOtp");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void back() throws IOException {
        App.setRoot("Login");
    }
}
