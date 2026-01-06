package lk.ijse.gymmembershipmanagementsystem.util;

import java.util.Random;

public class OTPUtil {
    public static String generate() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
