package com.example.walletmanagementsystem.service;

import src.dao.AssetDAO;
import src.dao.PortfolioDAO;
import src.dao.WalletDAO;
import src.model.Asset;
import src.model.Transaction;
import src.model.TransactionType;
import src.model.Wallet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TradeService {

    //Buy Asset
    public boolean buyAsset(int userId, String symbol, double quantity) {
        if (quantity <= 0) {
            return false;
        }

        Asset asset = AssetDAO.getAssetById(symbol);
        if (asset == null) {
            return false;
        }

        double totalCost = quantity * asset.getCurrentPrice();

        Wallet wallet = WalletDAO.getWalletById(userId);
        if (wallet == null || wallet.getBalance() < totalCost) {
            return false;
        }

        wallet.setBalance(wallet.getBalance() - totalCost);
        WalletDAO.updateWalletBalance(userId, wallet.getBalance());

        PortfolioDAO.upsertAsset(userId,asset,quantity);

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setType(TransactionType.BUY);
        transaction.setAssetSymbol("USD");
        transaction.setQuantity(quantity);
        transaction.setAmount(totalCost);
        transaction.setPriceAtTime(asset.getCurrentPrice());
        transaction.setDateTime(LocalDate.from(LocalDateTime.now()));

        return true;
    }

    //Sell Asset
    public boolean sellAsset(int userId, String symbol, double quantity) {
        if (quantity < 0) {
            return false;
        }

        Asset asset = AssetDAO.getAssetById(symbol);
        if (asset == null) {
            return false;
        }

        double ownedQty = PortfolioDAO.getAssetQuantity(userId, symbol);
        if (ownedQty < quantity) {
            return false;
        }

        double totalRevenue = quantity*asset.getCurrentPrice();

        PortfolioDAO.upsertAsset(userId,asset,quantity);

        Wallet wallet = WalletDAO.getWalletById(userId);
        wallet.setBalance(wallet.getBalance()+totalRevenue);
        WalletDAO.updateWalletBalance(userId,wallet.getBalance());

        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setType(TransactionType.BUY);
        transaction.setAssetSymbol("USD");
        transaction.setQuantity(quantity);
        transaction.setAmount(totalRevenue);
        transaction.setPriceAtTime(asset.getCurrentPrice());
        transaction.setDateTime(LocalDate.from(LocalDateTime.now()));

        return true;
    }

}
