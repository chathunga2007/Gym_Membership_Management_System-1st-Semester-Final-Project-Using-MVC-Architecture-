package lk.ijse.gymmembershipmanagementsystem.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtil {

    public static void sendOTP(String to, String otp) {

        final String from = "wggachathungabimsara2007@gmail.com";
        final String appPassword = "cdgx cbmg xjpz llhy"; // Gmail App Password

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
            msg.setSubject("Password Reset OTP – Gym Management System");

            String htmlContent =
                    "<div style='font-family: Arial, sans-serif; background:#f4f6f8; padding:20px'>" +
                            "  <div style='max-width:500px; margin:auto; background:#ffffff; padding:25px; border-radius:10px'>" +
                            "    <h2 style='color:#6a1b9a; text-align:center'>Gym Management System</h2>" +
                            "    <p>Hello,</p>" +
                            "    <p>You requested to reset your password. Please use the OTP below to continue:</p>" +

                            "    <div style='text-align:center; margin:25px 0'>" +
                            "       <span style='font-size:28px; letter-spacing:5px; " +
                            "       font-weight:bold; color:#ffffff; background:#6a1b9a; " +
                            "       padding:12px 25px; border-radius:8px; display:inline-block'>" +
                            otp +
                            "       </span>" +
                            "    </div>" +

                            "    <p>This OTP is valid for a short time only.</p>" +
                            "    <p style='color:#e53935'><b>Do not share this code with anyone.</b></p>" +

                            "    <hr>" +
                            "    <p style='font-size:12px; color:#777; text-align:center'>" +
                            "       If you did not request this, please ignore this email.<br>" +
                            "       © 2026 Gym Management System" +
                            "    </p>" +
                            "  </div>" +
                            "</div>";

            msg.setContent(htmlContent, "text/html");

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
