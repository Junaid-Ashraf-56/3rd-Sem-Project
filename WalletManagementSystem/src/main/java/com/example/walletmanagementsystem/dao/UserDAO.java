package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Role;
import com.example.walletmanagementsystem.model.User;
import com.example.walletmanagementsystem.service.UserService;


import java.sql.*;


public class UserDAO {



    static Connection connection = DBConnection.getConnection();


    //Add a new user
    public static User addUser(String name, String email, String password, Role role) {
        String accountNumber = UserService.generateAccountNumber();

        String sql = "INSERT INTO Users(name, email, password,accountnumber, role) VALUES (?, ?, ?, ?,?)";
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.setString(4, accountNumber);
                stmt.setString(5, String.valueOf(role));

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int id = rs.getInt(1);
                            return new User(id,name, email, password, role);
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



   //Get user id by email
   public static User getUserId(String accountNumber) {
       String sql = "SELECT * FROM Users WHERE email = ?";
       User user = null;

       if (connection != null) {
           try (PreparedStatement stmt = connection.prepareStatement(sql)) {
               stmt.setString(1, accountNumber);
               try (ResultSet rs = stmt.executeQuery()) {
                   if (rs.next()) {
                       user = new User();
                       user.setUserId(rs.getInt("id"));
                       user.setName(rs.getString("name"));
                       user.setEmail(rs.getString("email"));
                       user.setPassword(rs.getString("password"));
                       user.setAccountNumber(rs.getString("accountnumber"));
                       user.setRole(Role.valueOf(rs.getString("role")));
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

       return user;
   }

    //Update user
    public static boolean updateUser(User user){
    String sql = "Update Users SET name = ?,password = ?,role =? WHERE email = ?";
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
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
            User user = null;
            if (connection != null) {
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    stmt.setString(2, password);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            user = new User();
                            user.setUserId(rs.getInt("userId"));
                            user.setName(rs.getString("name"));
                            user.setEmail(rs.getString("email"));
                            user.setPassword(rs.getString("password"));
                            user.setAccountNumber(rs.getString("accountnumber"));
                            user.setRole(Role.valueOf(rs.getString("role")));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return user;

    }
//Verify and update password
    public static boolean verifyPassword(int userId, String inputPassword) {
        String query = "SELECT password FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(inputPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updatePassword(int userId, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword); // Hash here if needed
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //Freeze User
    public static boolean freezeUserByEmail(String email) {
        String sql = "UPDATE Users SET is_frozen = ? WHERE email = ?";
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

    //Unfreeze account
    public static boolean unfreezeUserByEmail(String email) {
        String sql = "UPDATE Users SET is_frozen = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, false);
            stmt.setString(2, email);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
