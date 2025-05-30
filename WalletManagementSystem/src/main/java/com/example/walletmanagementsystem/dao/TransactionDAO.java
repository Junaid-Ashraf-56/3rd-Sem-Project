package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Transaction;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TransactionDAO {
        //Inserting new Transaction
        public boolean insertNewTransaction(Transaction transaction){
                boolean isInserted = false;
                Connection connection = DBConnection.getConnection();
                String sql = "INSERT INTO transactions (user_id, type, asset_symbol, quantity, amount, price_at_time, date_time)  VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setInt(1, transaction.getUserId());
                        stmt.setString(2, transaction.getType().name());
                        stmt.setString(3, transaction.getAssetSymbol());
                        stmt.setDouble(4, transaction.getQuantity());
                        stmt.setDouble(5, transaction.getAmount());
                        stmt.setDouble(6, transaction.getPriceAtTime());
                        stmt.setTimestamp(7, java.sql.Timestamp.valueOf(transaction.getDateTime().atStartOfDay()));
                        isInserted = true;
                } catch (Exception e) {
                        e.printStackTrace();
                }
            return isInserted;
        }

        //Get transaction ny user id
        public static Transaction getTransactionsByUserId(int userId){
                String sql = "SELECT * FROM Transaction WHERE userId = ?";
                Transaction transaction = null;
                Connection connection = DBConnection.getConnection();
                try(PreparedStatement stmt = connection.prepareStatement(sql)){
                        stmt.setInt(1,userId);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()){
                                transaction = new Transaction();
                                transaction.setUserId(rs.getInt("userId"));
                                transaction.setTransactionId((rs.getInt("transactionId")));
                                transaction.setAmount(rs.getDouble("amount"));
                                transaction.setQuantity(rs.getDouble("quantity"));
                                transaction.setAssetSymbol(rs.getString("assetSymbol"));
                        }
                } catch (SQLException e) {
                  e.printStackTrace();
                }
            return transaction;
        }

        //Record Transactions
        public static boolean recordTransaction(int userId, double amount, String description) {
                String sql = "INSERT INTO transactions (user_id, amount, description, timestamp) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                        stmt.setInt(1, userId);
                        stmt.setDouble(2, amount);
                        stmt.setString(3, description);

                        int rowsInserted = stmt.executeUpdate();
                        return rowsInserted > 0;

                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return false;
        }

}



