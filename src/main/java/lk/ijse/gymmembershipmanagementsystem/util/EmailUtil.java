package lk.ijse.gymmembershipmanagementsystem.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    public static void sendOTP(String to, String otp) {

        final String from = "wggachathungabimsara2007@gmail.com";
        final String appPassword = "cdgx cbmg xjpz llhy";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, appPassword);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, "Gym Management System"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject("🔒 Secure OTP - Gym Management System");

            String htmlContent =
                    "<div style=\"font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f3f4f6; padding: 40px 10px;\">" +
                            "    <div style=\"max-width: 500px; margin: auto; background-color: #ffffff; border-radius: 16px; overflow: hidden; box-shadow: 0 10px 25px rgba(0,0,0,0.1);\">" +
                            "        " +
                            "        " +
                            "        <div style=\"background-color: #1a1a1a; padding: 30px; text-align: center;\">" +
                            "            <h1 style=\"color: #ffffff; margin: 0; font-size: 24px; letter-spacing: 1px; text-transform: uppercase;\">Gym Management System</h1>" +
                            "        </div>" +
                            "        " +
                            "        " +
                            "        <div style=\"padding: 40px; text-align: center;\">" +
                            "            <div style=\"margin-bottom: 20px;\">" +
                            "                <img src=\"https://cdn-icons-png.flaticon.com/128/6195/6195699.png\" width=\"60\" height=\"60\" alt=\"Security Icon\">" +
                            "            </div>" +
                            "            <h2 style=\"color: #333; margin-bottom: 10px;\">Verification Code</h2>" +
                            "            <p style=\"color: #666; font-size: 16px; line-height: 1.5;\">Hello,</p>" +
                            "            <p style=\"color: #666; font-size: 16px; line-height: 1.5;\">Use the following One-Time Password (OTP) to reset your password safely. This code is valid for 10 minutes.</p>" +
                            "            " +
                            "            " +
                            "            <div style=\"margin: 35px 0;\">" +
                            "                <span style=\"background-color: #f8f9fa; border: 2px dashed #e0e0e0; color: #d32f2f; font-size: 36px; font-weight: bold; letter-spacing: 8px; padding: 15px 30px; border-radius: 8px; display: inline-block;\">" +
                            otp +
                            "                </span>" +
                            "            </div>" +
                            "            " +
                            "            <p style=\"color: #999; font-size: 14px; margin-top: 25px;\">If you didn't request this, you can safely ignore this email.</p>" +
                            "        </div>" +
                            "        " +
                            "        " +
                            "        <div style=\"background-color: #f8f9fa; padding: 20px; text-align: center; border-top: 1px solid #eeeeee;\">" +
                            "            <p style=\"font-size: 12px; color: #888; margin: 0;\">© 2026 Gym Management System. All rights reserved.</p>" +
                            "            <p style=\"font-size: 12px; color: #888; margin-top: 5px;\">Stay Fit, Stay Strong!</p>" +
                            "        </div>" +
                            "    </div>" +
                            "</div>";

            msg.setContent(htmlContent, "text/html");

            Transport.send(msg);
            System.out.println("OTP successfully sent to: " + to);

        } catch (Exception e) {
            System.err.println("Error while sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}