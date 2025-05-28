//package src.dao;
//
//import src.config.DBConnection;
//import src.model.Asset;
//import src.model.AssetType;
//import src.model.Crypto;
////import src.model.Stock;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AssetDAO {
//    // 1. Insert new asset (from API or admin)
//    public static boolean insertAsset(Asset asset) {
//        String sql = "INSERT INTO assets ( name, current_price, asset_type, extra_info) VALUES (?, ?, ?, ?, ?)";
//        Connection conn = DBConnection.getConnection();
//
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, asset.getAssetID());
//            stmt.setString(2, asset.getName());
//            stmt.setDouble(3, asset.getCurrentPrice());
//            stmt.setString(4, asset.getAssetType().name());
//
//            // Determine extra info based on type
//            if (asset instanceof Stock) {
//                stmt.setString(5, ((Stock) asset).getExchange());
//            } else if (asset instanceof Crypto) {
//                stmt.setString(5, ((Crypto) asset).getBlockchain());
//            } else {
//                stmt.setString(5, null);
//            }
//
//            return stmt.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // 2. Get asset by ID
//    public static Asset getAssetById(String assetId) {
//        String sql = "SELECT * FROM assets WHERE asset_id = ?";
//        Asset asset = null;
//
//        Connection conn = DBConnection.getConnection();
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, assetId);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                String id = rs.getString("asset_id");
//                String name = rs.getString("name");
//                double price = rs.getDouble("current_price");
//                AssetType type = AssetType.valueOf(rs.getString("asset_type"));
//                String extra = rs.getString("extra_info");
//
//                if (type == AssetType.STOCK) {
//                    asset = new Stock(id, name, price, extra);
//                } else if (type == AssetType.CRYPTO) {
//                    asset = new Crypto(id, name, price, extra);
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return asset;
//    }
//
//    // 3. Update current price (on price refresh)
//    public static boolean updateAssetPrice(String assetId, double newPrice) {
//        String sql = "UPDATE assets SET current_price = ? WHERE asset_id = ?";
//
//        Connection conn = DBConnection.getConnection();
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setDouble(1, newPrice);
//            stmt.setString(2, assetId);
//            return stmt.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    // 4. Get a list of all available assets
//    public static List<Asset> getAllAssets(int userId) {
//        List<Asset> assets = new ArrayList<>();
//        String sql = "SELECT * FROM assets WHERE userId = ?";
//
//        Connection conn = DBConnection.getConnection();
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                String id = rs.getString("asset_id");
//                String name = rs.getString("name");
//                double price = rs.getDouble("current_price");
//                AssetType type = AssetType.valueOf(rs.getString("asset_type"));
//                String extra = rs.getString("extra_info");
//
//                Asset asset = (type == AssetType.STOCK)
//                        ? new Stock(id, name, price, extra)
//                        : new Crypto(id, name, price, extra);
//
//                assets.add(asset);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return assets;
//    }
//}
