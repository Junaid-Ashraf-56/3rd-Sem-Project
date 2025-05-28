//package src.service;
//
//import src.dao.TransactionDAO;
//import src.dao.WalletDAO;
//import src.model.Transaction;
//import src.model.TransactionType;
//import src.model.Wallet;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//public class WalletService {
//
//    //Add funds
//    public boolean addFunds(int userId ,double amount){
//        if (amount<=0){
//            return false;
//        }
//        Wallet wallet = WalletDAO.getWalletById(userId);
//        if (wallet==null){
//            return false;
//        }
//        double newBalance = wallet.getBalance()+amount;
//        wallet.setBalance(newBalance);
//
//        boolean updated = WalletDAO.updateWalletBalance(userId,newBalance);
//        if (updated) {
//            // Log transaction
//            Transaction tx = new Transaction();
//            tx.setUserId(userId);
//            tx.setType(TransactionType.ADD_FUNDS);
//            tx.setAmount(amount);
//            tx.setAssetSymbol("USD");
//            tx.setPriceAtTime(1.0);
//            tx.setQuantity(amount);
//            tx.setDateTime(LocalDate.from(LocalDateTime.now()));
////            TransactionDAO.insertTransaction(tx);
//        }
//
//        return updated;
//    }
//
//    //Withdraw funds
//    public boolean withdrawFunds(int userId , double amount ,double initialAmount){
//        if (amount<=0&&initialAmount<amount){
//            return false;
//        }
//        Wallet wallet =WalletDAO.getWalletById(userId);
//        if (wallet==null){
//            return false;
//        }
//        double newBalance = wallet.getBalance()-amount;
//        wallet.setBalance(newBalance);
//
//        boolean updated = WalletDAO.updateWalletBalance(userId,newBalance);
//
//        if (updated) {
//            Transaction tx = new Transaction();
//            tx.setUserId(userId);
//            tx.setType(TransactionType.WITHDRAW);
//            tx.setAmount(amount);
//            tx.setAssetSymbol("USD");
//            tx.setPriceAtTime(1.0);
//            tx.setQuantity(amount);
//            tx.setDateTime(LocalDate.from(LocalDateTime.now()));
//           // TransactionDAO.insertTransaction(tx);
//        }
//
//        return updated;
//
//    }
//
//    // ✅ Get current balance
//    public double getBalance(int userId) {
//        Wallet wallet = WalletDAO.getWalletById(userId);
//        return (wallet != null) ? wallet.getBalance() : 0.0;
//    }
//
//
//    //Transfer money from one account to another
//    public boolean transferFunds(int fromUserId,int toUserId,double amount){
//        Wallet fromWallet  = WalletDAO.getWalletById(fromUserId);
//        Wallet toWallet = WalletDAO.getWalletById(toUserId);
//
//        if (fromWallet==null||toWallet==null){
//            return false;
//        }
//        if (fromWallet.getBalance()<amount){
//            return false;
//        }
//        fromWallet.setBalance(fromWallet.getBalance()-amount);
//        toWallet.setBalance(toWallet.getBalance()+amount);
//
//        boolean updateFrom = WalletDAO.updateWalletBalance(fromUserId,fromWallet.getBalance());
//        boolean updateTo = WalletDAO.updateWalletBalance(toUserId,toWallet.getBalance());
//
//        if (updateFrom&&updateTo){
//            TransactionDAO.recordTransaction(fromUserId, -amount, "Transfer to user " + toUserId);
//            TransactionDAO.recordTransaction(toUserId, amount, "Received from user " + fromUserId);
//            return true;
//        }
//
//        return false;
//    }
//}
