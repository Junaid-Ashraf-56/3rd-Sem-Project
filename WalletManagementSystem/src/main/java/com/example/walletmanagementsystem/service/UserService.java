package com.example.walletmanagementsystem.service;


import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.Role;
import com.example.walletmanagementsystem.model.User;

public class UserService {
    public User getUser(String email){
        return UserDAO.getUserId(email);
    }


    public boolean resetPassword(String email,String password){
        User user = UserDAO.getUserId(email);
        if (user!=null){
            user.setPassword(password);
            return UserDAO.updateUser(user);
        }
        return false;
    }

    public boolean isAdmin(User user){
        return user!=null&&user.getRole()== Role.ADMIN;
    }

    public boolean isUser(User user){
        return user!=null&&user.getRole()==Role.USER;
    }

    public static String generateAccountNumber() {
        String accountNumber = "ACC ";
        for (int i = 0; i < 10; i++) {
            int digit = (int)(Math.random() * 10); // Now includes 0-9
            accountNumber += digit;
        }
        return accountNumber;
    }

}
