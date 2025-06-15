package com.example.walletmanagementsystem.dao;



import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminDAO {

    static Connection connection = DBConnection.getConnection();

    //View all User
    public static User viewAllUsers(){
        User user = null;
        String sql = "SELECT * FROM users";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password"));

                String accountNumber = rs.getString("wallet_id");
                Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
                user.setWallet(wallet);

                accountNumber = rs.getString("portfolio_id");
                Portfolio portfolio = PortfolioDAO.getPortfolioByAccountNumber(accountNumber);
                user.setPortfolio(portfolio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    //View all transactions
    public static List<Transaction> viewAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions";
        try(PreparedStatement stmt = connection.prepareStatement(sql) ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Transaction transaction = new Transaction();
                transaction.setTransactionId(transaction.getUserId());
                transaction.setType(TransactionType.valueOf(rs.getString("type")));
                transaction.setAssetSymbol(rs.getString("assetSymbol"));
                transaction.setQuantity(rs.getDouble("quantity"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setPriceAtTime(rs.getDouble("priceAtTime"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

// Create Report
}
