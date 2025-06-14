package com.example.walletmanagementsystem.utils;

public class ValidationUtil {
    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isStrongPassword(String password) {
        return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[A-Z].*");
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+(\\.\\d+)?");
    }
}
