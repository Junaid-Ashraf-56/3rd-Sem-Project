package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Asset;
import com.example.walletmanagementsystem.model.Portfolio;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PortfolioDAO {

    static Connection connection = DBConnection.getConnection();

    // Get portfolio by account number
    public static Portfolio getPortfolioByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM portfolio WHERE account_number = ?";
        Portfolio portfolio = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    portfolio = new Portfolio();
                    portfolio.setAccountNumber(rs.getString("account_number"));

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
        String sql = "INSERT INTO portfolio(account_number, portfolio_data) VALUES (?, ?)";

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
        String sql = "UPDATE portfolio SET portfolio_data = ? WHERE account_number = ?";
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
}