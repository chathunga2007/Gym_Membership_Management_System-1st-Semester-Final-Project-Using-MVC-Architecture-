package lk.ijse.gymmembershipmanagementsystem.controller;

import java.io.IOException;
import lk.ijse.gymmembershipmanagementsystem.App;

public class WelcomePageController {
    public void login() throws IOException {
        App.setRoot("Login", 1050, 665);
    }
}