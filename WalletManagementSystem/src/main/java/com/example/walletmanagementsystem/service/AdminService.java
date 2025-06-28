package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.User;

public class AdminService {


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
    public boolean unFreezeAccount(String email){
        User user = UserDAO.getUserId(email);
        if (user!=null){
            System.out.println("User not found");
            return false;
        }
        else {
            boolean result = UserDAO.unfreezeUserByEmail(email);
            if (result){
                System.out.println("Account unfreeze");
            }
            else {
                System.out.println("Error Occur");
            }
        return result;
        }
    }
}
