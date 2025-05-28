//package src.dao;
//
//import src.config.DBConnection;
//import src.model.Transaction;
//import src.model.TransactionType;
//import src.model.Wallet;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class WalletDAO {
//
//    //Get wallet by wallet ID
//    public static Wallet getWalletById(int walletId){
//        String sql = "Select * from wallet where id = ?";
//        Connection connection = DBConnection.getConnection();
//        Wallet wallet = null;
//        if (connection != null){
//
//            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
//                stmt.setInt(1,walletId);
//                ResultSet rs = stmt.executeQuery();
//                    if(rs.next()) {
//                        wallet = new Wallet();
//                        wallet.setWalletId(rs.getInt("id"));
//                        wallet.setUserId(rs.getInt("user_id"));
//                        wallet.setBalance(rs.getDouble("Balance"));
//                        wallet.setWallet(new ArrayList<>());
//                    }
//
//            }
//            catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//        return wallet;
//    }
//
//        //update wallet
//        public static boolean updateWalletBalance(int walletId, double newBalance){
//            boolean isUpdated = false;
//            String sql = "UPDATE  wallet SET balance = ? Where walletId = ?";
//            Connection connection = DBConnection.getConnection();
//        if(connection !=null){
//            try (PreparedStatement stmt = connection.prepareStatement(sql)){
//                stmt.setDouble(1, newBalance);
//                stmt.setInt(2,walletId);
//                stmt.executeUpdate();
//                isUpdated = true;
//
//            }catch(SQLException e){
//                e.printStackTrace();
//            }
//        }
//            return isUpdated;
//        }
//        //Insert into wallet
//        public static Wallet insertNewWallet(int userId){
//            String sql = "INSERT INTO wallet (userId, balance) VALUES (?, ?)";
//            Wallet wallet = null;
//            Connection connection = DBConnection.getConnection();
//            if (connection != null){
//                try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
//                    stmt.setInt(1, userId);
//                    stmt.setDouble(2, 0.0);
//
//                    int rowsInserted = stmt.executeQuery().findColumn(String.valueOf(1));
//
//                    if (rowsInserted > 0){
//                        try (ResultSet rs = stmt.getGeneratedKeys()){
//                            if (rs.next()){
//                                int walletId = rs.getInt(1);
//                                wallet = new Wallet();
//                                wallet.setUserId(walletId);
//                                wallet.setUserId(userId);
//                                wallet.setBalance(0.0);
//                                wallet.setWallet(new ArrayList<>());
//                            }
//
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return wallet;
//        }
//
//        // Get transaction by user id
//        public static List<Transaction> getTransactionByUserId(int userId){
//        String sql = "SELECT * FROM Transactions WHERE user_id = ?";
//        List<Transaction> transactions = new ArrayList<>();
//            Connection connection = DBConnection.getConnection();
//        if (connection != null){
//            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
//                stmt.setInt(1, userId);
//                ResultSet rs = stmt.executeQuery();
//                while (rs.next()){
//                    Transaction transaction = new Transaction();
//                    transaction.setTransactionId(rs.getInt("id"));
//                    transaction.setUserId(rs.getInt("user_id"));
//                    transaction.setType(TransactionType.valueOf(rs.getString("type")));
//                    transaction.setAssetSymbol(rs.getString("asset_symbol"));
//                    transaction.setQuantity(rs.getDouble("quantity"));
//                    transaction.setAmount(rs.getDouble("amount"));
//                    transactions.add(transaction);
//                }
//            }catch (SQLException e){
//                e.printStackTrace();
//            }
//        }
//            return transactions;
//        }
//
//}
