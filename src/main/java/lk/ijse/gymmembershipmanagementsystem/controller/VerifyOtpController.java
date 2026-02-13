package lk.ijse.gymmembershipmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.gymmembershipmanagementsystem.App;

public class VerifyOtpController {
    @FXML
    private TextField otpField;

    private final PasswordResetController pWordReset = new PasswordResetController();

    @FXML
    void verify() throws Exception {
        if (pWordReset.verifyOTP(ForgotPasswordController.email, otpField.getText())) {
            App.setRoot("ResetPassword");
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid OTP").show();
        }
    }
}
