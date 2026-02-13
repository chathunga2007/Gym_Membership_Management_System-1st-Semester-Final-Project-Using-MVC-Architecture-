package lk.ijse.gymmembershipmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import lk.ijse.gymmembershipmanagementsystem.App;

public class ResetPasswordController {
    @FXML
    private PasswordField newPasswordField;

    private final PasswordResetController pWordController = new PasswordResetController();

    @FXML
    void reset() throws Exception {
        pWordController.resetPassword(
                ForgotPasswordController.email,
                newPasswordField.getText()
        );
        new Alert(Alert.AlertType.INFORMATION, "Password Updated Successfully").show();
        App.setRoot("Login");
    }
}
