package com.example.walletmanagementsystem.service;


import com.example.walletmanagementsystem.dao.UserDAO;
import com.example.walletmanagementsystem.model.Role;
import com.example.walletmanagementsystem.model.User;

public class UserService {
//    public boolean registerUser(User user){
//        User existing = UserDAO.getUserId(user.getEmail());
//        if (existing!=null){
//            return false;
//        }
//
//        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()){
//            return false;
//        }
//        return UserDAO.addUser(user);
//    }
//
//    public User login(String email,String password){
//        if (email==null&&password==null){
//            return null;
//        }
//        return UserDAO.Login(email,password);
//    }

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

}
