package com.example.walletmanagementsystem.dao;

//import src.config.DBConnection;
//import src.model.*;

import com.example.walletmanagementsystem.config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminDAO {

    //View all User
    public static src.model.User viewAllUsers(){
        src.model.User user = null;
        String sql = "SELECT * FROM users";
        Connection connection = DBConnection.getConnection();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                user = new src.model.User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(src.model.Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password"));

                int walletId = rs.getInt("wallet_id");
                src.model.Wallet wallet = src.dao.WalletDAO.getWalletById(walletId);
                user.setWallet(wallet);

                int portfolioId = rs.getInt("portfolio_id");
                src.model.Portfolio portfolio = PortfolioDAO.getPortfolioByUserId(portfolioId);
                user.setPortfolio(portfolio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    //View all transactions
    public static List<src.model.Transaction> viewAllTransactions(){
        List<src.model.Transaction> transactions = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM Transactions";
        try(PreparedStatement stmt = connection.prepareStatement(sql) ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                src.model.Transaction transaction = new src.model.Transaction();
                transaction.setTransactionId(transaction.getUserId());
                transaction.setType(src.model.TransactionType.valueOf(rs.getString("type")));
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
