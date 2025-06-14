package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.User;

public class AdminService {

    //View  user
    public User getUser(String email){
        return UserDAO.getUserId(email);
    }

    public boolean freezeAccount(String email){
        User user = UserDAO.getUserId(email);
        if (user==null){
            System.out.println("User not found ");
            return false;
        }
        else {
            boolean result = UserDAO.freezeUserByEmail(email);
            if (result){
                System.out.println("Account freeze ");
            }
            else {
                System.out.println("Error occur ");
            }
            return result;
        }
    }
}
