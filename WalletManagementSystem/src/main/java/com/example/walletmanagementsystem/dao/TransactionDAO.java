package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Transaction;
import com.example.walletmanagementsystem.model.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private static final Connection connection = DBConnection.getConnection();

    // Insert new transaction
    public boolean insertNewTransaction(Transaction transaction) {
        boolean isInserted = false;
        String sql = "INSERT INTO Transaction (account_number, type, asset_symbol, quantity, amount, price_at_time, date_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, transaction.getAccountNumber());
                stmt.setString(2, transaction.getType().name());
                stmt.setString(3, transaction.getAssetSymbol());
                stmt.setDouble(4, transaction.getQuantity());
                stmt.setDouble(5, transaction.getAmount());
                stmt.setDouble(6, transaction.getPriceAtTime());
                stmt.setTimestamp(7, Timestamp.valueOf(transaction.getDateTime().atStartOfDay()));
                isInserted = stmt.executeUpdate() > 0; // Check if insertion was successful
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isInserted;
    }

    // Get transactions by account number
    public static List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM Transaction WHERE account_number = ?";
        List<Transaction> transactions = new ArrayList<>();
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, accountNumber);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("id"));
                    transaction.setAccountNumber(rs.getString("account_number"));
                    transaction.setType(TransactionType.valueOf(rs.getString("type")));
                    transaction.setAssetSymbol(rs.getString("asset_symbol"));
                    transaction.setQuantity(rs.getDouble("quantity"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setPriceAtTime(rs.getDouble("price_at_time"));
                    transaction.setDateTime(LocalDate.from(rs.getTimestamp("date_time").toLocalDateTime()));
                    transactions.add(transaction);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }

    // Record transaction
    public static boolean recordTransaction(String accountNumber, double amount, String description) {
        String sql = "INSERT INTO Transaction (account_number, amount, description, date_time) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        if (connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, accountNumber);
                stmt.setDouble(2, amount);
                stmt.setString(3, description);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}