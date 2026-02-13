package lk.ijse.gymmembershipmanagementsystem.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.gymmembershipmanagementsystem.App;
import lk.ijse.gymmembershipmanagementsystem.model.UserModel;

public class LoginController {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;

    private final UserModel user = new UserModel();
    
    @FXML
    private void back() throws IOException {
        App.setRoot("WelcomePage");
    }

    @FXML
    private void login() {
        try {
            String uName = userNameField.getText();
            String pWord = passwordField.getText();

            if (user.checkLogin(uName, pWord)) {
                System.out.println("Logged-in Successfully!");
                App.setRoot("DashBoard", 1300,790);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Message");
                alert.setHeaderText("Invalid user name or password");
                alert.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Cannot connect to database or query failed");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void forgotPassword() throws IOException {
        App.setRoot("ForgotPassword");
    }
}
