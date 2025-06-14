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
    //Get id by Portfolio id
    public static Portfolio getPortfolioByUserId(int portfolioId){
        String sql = "SELECT * FROM portfolio WHERE id = ?";
        Portfolio portfolio = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, portfolioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    portfolio = new Portfolio();
                    portfolio.setPortfolioId(rs.getInt("id"));
                    portfolio.setUserId(rs.getInt("userId"));

                    // Jackson object mapper to parse JSON
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonPortfolio = rs.getString("portfolio");
                    HashMap<String, Asset> portfolioMap = mapper.readValue(
                            jsonPortfolio,
                            new TypeReference<>() {}
                    );
                    portfolio.setPortfolio(portfolioMap);  // Your Portfolio class should have setPortfolio(HashMap<String, Double>)
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return portfolio;
    }


    // Insert New Portfolio JSON
    public static void insertPortfolio(int userId,Portfolio portfolio) {
        String sql = "INSERT INTO portfolio(user_id, portfolio_data) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, portfolio.getUserId());

            // Convert HashMap to JSON string
            ObjectMapper mapper = new ObjectMapper();
            String jsonPortfolio = mapper.writeValueAsString(portfolio.getPortfolio());

            stmt.setString(2, jsonPortfolio);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Portfolio JSON by ID
    public static boolean updatePortfolio(int userId,Portfolio portfolio) {
        String sql = "UPDATE portfolio SET portfolio_data = ? WHERE id = ?";
        boolean success = false;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonPortfolio = mapper.writeValueAsString(portfolio.getPortfolio());

            stmt.setString(1, jsonPortfolio);
            stmt.setInt(2, portfolio.getPortfolioId());

            success = stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }


    //upsert means insert and update in parallel
    public static boolean upsertAsset(int userId, Asset asset, double quantity) {
        Portfolio portfolio = getPortfolioByUserId(userId); // You must implement this
        if (portfolio == null) {
            return false;
        }

        Map<String, Asset> assets = portfolio.getPortfolio();
        String symbol = asset.getAssetSymbol();
        if (assets.containsKey(symbol)) {
            Asset existingAsset = assets.get(symbol);
            existingAsset.setQuantity((existingAsset.getQuantity() + quantity));
        } else {
           return false;
        }

        // Save updated portfolio
        return updatePortfolio(userId, portfolio);
    }

     // getAssetQuantity
    public static double getAssetQuantity(int userId, String assetSymbol) {
        Portfolio portfolio = getPortfolioByUserId(userId); // You must implement this
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
