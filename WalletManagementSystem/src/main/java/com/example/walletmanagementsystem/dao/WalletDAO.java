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
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, accountNumber);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    wallet = new Wallet();
                    wallet.setWalletId(rs.getInt("id"));
                    wallet.setUserId(rs.getInt("userid")); // Keep if user_id is still needed
                    wallet.setAccountNumber(rs.getString("accountnumber")); // Changed to match DB column
                    wallet.setBalance(rs.getDouble("balance"));
                    wallet.setWallet(new ArrayList<>());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wallet;
    }

    // Update wallet balance
    public static boolean updateWalletBalance(String accountNumber, double newBalance) {
        boolean isUpdated = false;
        String sql = "UPDATE wallet SET balance = ? WHERE accountnumber = ?";
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setDouble(1, newBalance);
                stmt.setString(2, accountNumber);
                isUpdated = stmt.executeUpdate() > 0; // Check if update was successful
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }

    // Insert new wallet
    public static Wallet insertNewWallet(String accountNumber) {
        String sql = "INSERT INTO wallet (accountnumber, balance, userid) VALUES (?, ?, ?)";
        Wallet wallet = null;
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, accountNumber);
                stmt.setDouble(2, 0.0);
                stmt.setInt(3, 0); // Set user_id to 0 or handle as needed
                int rowsInserted = stmt.executeUpdate(); // Use executeUpdate for INSERT

                if (rowsInserted > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int walletId = rs.getInt(1);
                            wallet = new Wallet();
                            wallet.setWalletId(walletId);
                            wallet.setUserId(0); // Set user_id to 0 or handle as needed
                            wallet.setAccountNumber(accountNumber);
                            wallet.setBalance(0.0);
                            wallet.setWallet(new ArrayList<>());
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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