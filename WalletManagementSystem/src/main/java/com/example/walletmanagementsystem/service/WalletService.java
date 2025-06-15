package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.dao.TransactionDAO;
import com.example.walletmanagementsystem.dao.WalletDAO;
import com.example.walletmanagementsystem.model.Transaction;
import com.example.walletmanagementsystem.model.TransactionType;
import com.example.walletmanagementsystem.model.Wallet;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WalletService {

    // Add funds
    public boolean addFunds(String accountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
        if (wallet == null) {
            return false;
        }
        double newBalance = wallet.getBalance() + amount;
        wallet.setBalance(newBalance);

        boolean updated = WalletDAO.updateWalletBalance(accountNumber, newBalance);
        if (updated) {
            Transaction tx = new Transaction();
            tx.setAccountNumber(accountNumber);
            tx.setType(TransactionType.ADD_FUNDS);
            tx.setAmount(amount);
            tx.setAssetSymbol("USD");
            tx.setPriceAtTime(1.0);
            tx.setQuantity(amount);
            tx.setDateTime(LocalDate.from(LocalDateTime.now()));
            // TransactionDAO.insertTransaction(tx);
        }

        return updated;
    }

    // Withdraw funds
    public boolean withdrawFunds(String accountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
        if (wallet == null || wallet.getBalance() < amount) {
            return false;
        }
        double newBalance = wallet.getBalance() - amount;
        wallet.setBalance(newBalance);

        boolean updated = WalletDAO.updateWalletBalance(accountNumber, newBalance);

        if (updated) {
            Transaction tx = new Transaction();
            tx.setAccountNumber(accountNumber);
            tx.setType(TransactionType.WITHDRAW);
            tx.setAmount(amount);
            tx.setAssetSymbol("USD");
            tx.setPriceAtTime(1.0);
            tx.setQuantity(amount);
            tx.setDateTime(LocalDate.from(LocalDateTime.now()));
            // TransactionDAO.insertTransaction(tx);
        }

        return updated;
    }

    // Get current balance
    public double getBalance(String accountNumber) {
        Wallet wallet = WalletDAO.getWalletByAccountNumber(accountNumber);
        return (wallet != null) ? wallet.getBalance() : 0.0;
    }

    // Transfer money from one account to another
    public boolean transferFunds(String fromAccountNumber, String toAccountNumber, double amount) {
        Wallet fromWallet = WalletDAO.getWalletByAccountNumber(fromAccountNumber);
        Wallet toWallet = WalletDAO.getWalletByAccountNumber(toAccountNumber);

        if (fromWallet == null || toWallet == null || amount <= 0) {
            return false;
        }
        if (fromWallet.getBalance() < amount) {
            return false;
        }
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);

        boolean updateFrom = WalletDAO.updateWalletBalance(fromAccountNumber, fromWallet.getBalance());
        boolean updateTo = WalletDAO.updateWalletBalance(toAccountNumber, toWallet.getBalance());

        if (updateFrom && updateTo) {
            TransactionDAO.recordTransaction(fromAccountNumber, -amount, "Transfer to account " + toAccountNumber);
            TransactionDAO.recordTransaction(toAccountNumber, amount, "Received from account " + fromAccountNumber);
            return true;
        }

        return false;
    }
}