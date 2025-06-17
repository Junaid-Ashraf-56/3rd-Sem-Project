package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Transaction;
import com.example.walletmanagementsystem.model.TransactionType;
import com.example.walletmanagementsystem.model.Wallet;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class WalletDAO {

    private static final Connection connection = DBConnection.getConnection();

    // Get wallet by account number
    public static Wallet getWalletByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM wallet WHERE accountnumber = ?";
        Wallet wallet = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    wallet = new Wallet();
                    wallet.setWalletId(rs.getInt("walletid"));
                    wallet.setAccountNumber(rs.getString("accountnumber"));
                    wallet.setBalance(rs.getDouble("balance"));
                    wallet.setUserId(rs.getInt("userid"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wallet;
    }


    // Update wallet balance
    public static boolean updateWalletBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE wallet SET balance = ? WHERE accountnumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);
            int rowsUpdated = stmt.executeUpdate();
            System.out.println("Updated rows: " + rowsUpdated);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Insert new wallet
    public static Wallet insertNewWallet(String accountNumber, int userId, Connection conn) {
        String sql = "INSERT INTO wallet (accountnumber, balance, userid) VALUES (?, ?, ?)";
        Wallet wallet = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, accountNumber);
            stmt.setDouble(2, 0.0);
            stmt.setInt(3, userId);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int walletId = rs.getInt(1);
                        wallet = new Wallet();
                        wallet.setWalletId(walletId);
                        wallet.setUserId(userId);
                        wallet.setAccountNumber(accountNumber);
                        wallet.setBalance(0.0);
                        wallet.setWallet(new ArrayList<>());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wallet;
    }



    // Get transactions by account number
    public static List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM Transaction WHERE accountnumber = ?";
        List<Transaction> transactions = new ArrayList<>();
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, accountNumber);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("id"));
                    transaction.setUserId(rs.getInt("user_id")); // Keep if user_id is still needed
                    transaction.setAccountNumber(rs.getString("accountnumber"));
                    transaction.setType(TransactionType.valueOf(rs.getString("type")));
                    transaction.setAssetSymbol(rs.getString("asset_symbol"));
                    transaction.setQuantity(rs.getDouble("quantity"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transactions.add(transaction);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }



}