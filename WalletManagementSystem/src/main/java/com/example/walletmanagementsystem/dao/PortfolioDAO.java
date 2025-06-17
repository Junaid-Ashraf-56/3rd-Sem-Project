package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Asset;
import com.example.walletmanagementsystem.model.Portfolio;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PortfolioDAO {

    static Connection connection = DBConnection.getConnection();

    // Get portfolio by account number
    public static Portfolio getPortfolioByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM portfolio WHERE accountnumber = ?";
        Portfolio portfolio = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    portfolio = new Portfolio();
                    portfolio.setAccountNumber(rs.getString("accountnumber"));

                    // Jackson object mapper to parse JSON
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonPortfolio = rs.getString("portfolio_data");
                    HashMap<String, Asset> portfolioMap = mapper.readValue(
                            jsonPortfolio,
                            new TypeReference<>() {}
                    );
                    portfolio.setPortfolio(portfolioMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portfolio;
    }

    // Insert new portfolio JSON
    public static void insertPortfolio(int accountNumber, Portfolio portfolio) {
        String sql = "INSERT INTO portfolio(accountnumber, portfolio_data) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountNumber);

            // Convert HashMap to JSON string
            ObjectMapper mapper = new ObjectMapper();
            String jsonPortfolio = mapper.writeValueAsString(portfolio.getPortfolio());

            stmt.setString(2, jsonPortfolio);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update portfolio JSON by account number
    public static boolean updatePortfolio(String accountNumber, Portfolio portfolio) {
        String sql = "UPDATE portfolio SET portfolio_data = ? WHERE accountnumber = ?";
        boolean success = false;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPortfolio = mapper.writeValueAsString(portfolio.getPortfolio());

            stmt.setString(1, jsonPortfolio);
            stmt.setString(2, accountNumber);

            success = stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    // Upsert asset (insert and update in parallel)
    public static boolean upsertAsset(String accountNumber, Asset asset, double quantity) {
        Portfolio portfolio = getPortfolioByAccountNumber(accountNumber);
        if (portfolio == null) {
            return false;
        }

        Map<String, Asset> assets = portfolio.getPortfolio();
        String symbol = asset.getAssetSymbol();
        if (assets.containsKey(symbol)) {
            Asset existingAsset = assets.get(symbol);
            existingAsset.setQuantity(existingAsset.getQuantity() + quantity);
        } else {
            return false;
        }

        // Save updated portfolio
        return updatePortfolio(accountNumber, portfolio);
    }

    // Get asset quantity
    public static double getAssetQuantity(String accountNumber, String assetSymbol) {
        Portfolio portfolio = getPortfolioByAccountNumber(accountNumber);
        if (portfolio == null) {
            return 0.0;
        }

        Map<String, Asset> assets = portfolio.getPortfolio();

        if (assets.containsKey(assetSymbol)) {
            return assets.get(assetSymbol).getQuantity();
        } else {
            return 0.0;
        }
    }

    public static void reduceAsset(String accountNumber, Asset asset, double quantityToReduce) {
        String selectSQL = "SELECT quantity FROM portfolio WHERE account_number = ? AND asset_symbol = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectSQL);) {
            selectStmt.setString(1, accountNumber);
            selectStmt.setString(2, asset.getSymbol());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                double existingQty = rs.getDouble("quantity");
                double newQty = existingQty - quantityToReduce;

                if (newQty > 0) {
                    String updateSQL = "UPDATE portfolio SET quantity = ? WHERE account_number = ? AND asset_symbol = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
                    updateStmt.setDouble(1, newQty);
                    updateStmt.setString(2, accountNumber);
                    updateStmt.setString(3, asset.getSymbol());
                    updateStmt.executeUpdate();
                } else {
                    // Remove the asset from portfolio if quantity becomes 0 or less
                    String deleteSQL = "DELETE FROM portfolio WHERE account_number = ? AND asset_symbol = ?";
                    PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL);
                    deleteStmt.setString(1, accountNumber);
                    deleteStmt.setString(2, asset.getSymbol());
                    deleteStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}