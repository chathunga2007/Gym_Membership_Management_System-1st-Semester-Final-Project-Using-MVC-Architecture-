package lk.ijse.gymmembershipmanagementsystem.controller;

import lk.ijse.gymmembershipmanagementsystem.model.PasswordResetModel;
import lk.ijse.gymmembershipmanagementsystem.model.UserModel;
import lk.ijse.gymmembershipmanagementsystem.util.EmailUtil;
import lk.ijse.gymmembershipmanagementsystem.util.OTPUtil;

public class PasswordResetController {
    private final UserModel user = new UserModel();
    private final PasswordResetModel otpModel = new PasswordResetModel();

    public void sendOTP(String email) throws Exception {
        if (!user.emailExists(email))
            throw new RuntimeException("Email not found!");

        String otp = OTPUtil.generate();
        otpModel.saveOTP(email, otp);
        EmailUtil.sendOTP(email, otp);
    }

    public boolean verifyOTP(String email, String otp) throws Exception {
        return otpModel.verifyOTP(email, otp);
    }

    public void resetPassword(String email, String password) throws Exception {
        user.updatePassword(email, password);
        otpModel.clearOTP(email);
    }
}
