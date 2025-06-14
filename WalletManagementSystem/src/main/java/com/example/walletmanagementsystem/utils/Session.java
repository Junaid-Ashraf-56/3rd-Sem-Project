package com.example.walletmanagementsystem.utils;

import com.example.walletmanagementsystem.model.User;

public class Session {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getUserId() {
        return currentUser != null ? currentUser.getUserId() : -1;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
