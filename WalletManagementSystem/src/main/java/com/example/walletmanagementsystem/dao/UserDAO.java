package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Role;
import com.example.walletmanagementsystem.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {



    static Connection connection = DBConnection.getConnection();


    //Add a new user
public static void addUser(User user){
    String sql = "INSERT INTO user(name,email,password,role) VALUES(?,?,?,?)";
    if (connection!=null){
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4,user.getRole().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


   //Get user id by email
    public static User getUserId(String email){
    String sql = "SELECT * FROM user WHERE email = ?";
               User user = null;

    if (connection!=null){
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,email);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                  user = new User();
                  user.setUserId(rs.getInt("id"));
                  user.setName(rs.getString("name"));
                  user.setEmail(rs.getString("email"));
                  user.setPassword(rs.getString("password"));
                  user.setRole(Role.valueOf(rs.getString("role")));
                }
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }
    return user;
    }
              //Update user
    public static boolean updateUser(User user){
    String sql = "Update user SET name = ?,password = ?,role =? WHERE email = ?";
               boolean update = false;
    if (connection!=null){
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3,user.getRole().name());
            stmt.setString(4, user.getEmail());

            int affectedRow = stmt.executeUpdate();
            update = affectedRow>0;
            System.out.println(update?"User update successfully":"User not found "+user.getUserId());
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }
    return update;
    }

    //Login User
    public static User Login(String email,String password) {
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            User user = null;
            if (connection != null) {
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    stmt.setString(2, password);
                    try (ResultSet rs = stmt.executeQuery()) {
                        user = new User();
                        user.setUserId(rs.getInt("userId"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(Role.valueOf(rs.getString("role")));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return user;

    }

    //Freeze User
    public static boolean freezeUserByEmail(String email) {
        String sql = "UPDATE users SET is_frozen = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            stmt.setString(2, email);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
