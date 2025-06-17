package com.example.walletmanagementsystem.dao;

import com.example.walletmanagementsystem.config.DBConnection;
import com.example.walletmanagementsystem.model.Asset;
import com.example.walletmanagementsystem.model.AssetType;
import com.example.walletmanagementsystem.model.Crypto;
import com.example.walletmanagementsystem.model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssetDAO {

    static Connection connection = DBConnection.getConnection();

    // 1. Insert new asset (from API or admin)
    public static boolean insertAsset(Asset asset) {
        String sql = "INSERT INTO asset(assetid, name, currentprice, assettype, quantity, assetsymbol) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, asset.getAssetId());
            stmt.setString(2, asset.getName());
            stmt.setDouble(3, asset.getCurrentPrice());
            stmt.setString(4, asset.getAssetType().name());
            stmt.setDouble(5, asset.getQuantity());
            stmt.setString(6, asset.getAssetSymbol());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Get asset by ID
    public static Asset getAssetById(String coin) {
        String sql = "SELECT * FROM asset WHERE assetid = ?";
        Asset asset = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, coin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("assetid");
                String name = rs.getString("name");
                double price = rs.getDouble("currentprice");
                AssetType type = AssetType.valueOf(rs.getString("assettype"));
                double quantity = rs.getDouble("quantity");
                String symbol = rs.getString("assetsymbol");

                if (type == AssetType.STOCK) {
                    asset = new Stock(id, name, price, symbol, quantity);
                } else if (type == AssetType.CRYPTO) {
                    asset = new Crypto(id, name, price, symbol, quantity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asset;
    }

    // 3. Update current price (on price refresh)
    public static boolean updateAssetPrice(String assetId, double newPrice) {
        String sql = "UPDATE asset SET currentprice = ? WHERE assetid = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newPrice);
            stmt.setString(2, assetId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Get all available assets
    public static List<Asset> getAllAssets() {
        List<Asset> assets = new ArrayList<>();
        String sql = "SELECT * FROM asset";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("assetid");
                String name = rs.getString("name");
                double price = rs.getDouble("currentprice");
                AssetType type = AssetType.valueOf(rs.getString("assettype"));
                double quantity = rs.getDouble("quantity");
                String symbol = rs.getString("assetsymbol");

                Asset asset = (type == AssetType.STOCK)
                        ? new Stock(id, name, price, symbol, quantity)
                        : new Crypto(id, name, price, symbol, quantity);

                assets.add(asset);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assets;
    }
}
