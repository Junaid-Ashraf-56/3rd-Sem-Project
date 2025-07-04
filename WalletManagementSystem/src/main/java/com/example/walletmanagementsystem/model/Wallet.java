package com.example.walletmanagementsystem.model;

import java.util.ArrayList;


public class Wallet {
    private int walletId;
    private int userId;
    private double balance;
    private String accountNumber;
    ArrayList<Transaction> Wallet;
    // Constructors
    public Wallet() {
    }

    public Wallet(int walletId, int userId, double balance, ArrayList<Transaction> wallet) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = balance;
        Wallet = wallet;
    }

    public Wallet(int walletId, int userId, double balance, String accountNumber, ArrayList<Transaction> wallet) {
        this.walletId = walletId;
        this.userId = userId;
        this.balance = balance;
        this.accountNumber = accountNumber;
        Wallet = wallet;
    }

    // Getter and Setter for Wallet
    public void setWalletId(int walletId) {this.walletId = walletId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setWallet(ArrayList<Transaction> wallet) {Wallet = wallet;}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getWalletId() {return walletId;}
    public int getUserId() {return userId;}
    public double getBalance() {return balance;}
    public ArrayList<Transaction> getWallet() {return Wallet;}
}
