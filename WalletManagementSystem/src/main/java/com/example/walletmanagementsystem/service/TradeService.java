package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.dao.AssetDAO;
import com.example.walletmanagementsystem.dao.PortfolioDAO;
import com.example.walletmanagementsystem.dao.TransactionDAO;
import com.example.walletmanagementsystem.dao.WalletDAO;
import com.example.walletmanagementsystem.model.Asset;
import com.example.walletmanagementsystem.model.Transaction;
import com.example.walletmanagementsystem.model.TransactionType;
import com.example.walletmanagementsystem.model.Wallet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TradeService {

    // Buy Asset
    public boolean buyAsset(String accountNumber, String symbol, double quantity) {
        if (quantity <= 0) return false;

        Asset asset = AssetDAO.getAssetById(symbol);
        if (asset == null){
            return false;
        }

        double totalCost = quantity * asset.getCurrentPrice();

        Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
        if (wallet == null || wallet.getBalance() < totalCost) return false;


        wallet.setBalance(wallet.getBalance() - totalCost);
        WalletDAO.updateWalletBalance(accountNumber, wallet.getBalance());


        PortfolioDAO.upsertAsset(accountNumber, asset, quantity);

        // Create and store transaction
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setType(TransactionType.BUY);
        transaction.setAssetSymbol(symbol);
        transaction.setQuantity(quantity);
        transaction.setAmount(totalCost);
        transaction.setPriceAtTime(asset.getCurrentPrice());
        transaction.setDateTime(LocalDate.from(LocalDateTime.now()));

        TransactionDAO.insertNewTransaction(transaction);

        return true;
    }

    // Sell Asset
    public boolean sellAsset(String accountNumber, String coin, double quantity) {
        if (quantity <= 0) return false;

        Asset asset = AssetDAO.getAssetById(coin);
        if (asset == null) return false;

        double ownedQty = PortfolioDAO.getAssetQuantity(accountNumber, coin);
        if (ownedQty < quantity) return false;

        double totalRevenue = quantity * asset.getCurrentPrice();


        PortfolioDAO.reduceAsset(accountNumber, asset, quantity);


        Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
        wallet.setBalance(wallet.getBalance() + totalRevenue);
        WalletDAO.updateWalletBalance(accountNumber, wallet.getBalance());


        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setType(TransactionType.SELL);  // FIXED: SELL not BUY
        transaction.setAssetSymbol(coin);
        transaction.setQuantity(quantity);
        transaction.setAmount(totalRevenue);
        transaction.setPriceAtTime(asset.getCurrentPrice());
        transaction.setDateTime(LocalDate.from(LocalDateTime.now()));

        TransactionDAO.insertNewTransaction(transaction);

        return true;
    }
}
