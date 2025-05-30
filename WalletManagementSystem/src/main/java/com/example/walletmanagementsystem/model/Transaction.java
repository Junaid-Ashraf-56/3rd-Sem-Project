package com.example.walletmanagementsystem.model;

import java.time.LocalDate;


public class Transaction {
    private int transactionId;
    private int userId;
    private TransactionType type;
    private String assetSymbol;
    private double quantity;
    private double amount;
    private double priceAtTime;
    private LocalDate dateTime;


    // Constructors
    public Transaction() {
    }

    public Transaction(int transactionId, int userId, TransactionType type, String assetSymbol, double quantity, double amount, double priceAtTime, LocalDate dateTime) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.type = type;
        this.assetSymbol = assetSymbol;
        this.quantity = quantity;
        this.amount = amount;
        this.priceAtTime = priceAtTime;
        this.dateTime = dateTime;
    }


    // Getter and Setter for Transaction
    public void setTransactionId(int transactionId) {this.transactionId = transactionId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setType(TransactionType type) {this.type = type;}
    public void setAssetSymbol(String assetSymbol) {this.assetSymbol = assetSymbol;}
    public void setQuantity(double quantity) {this.quantity = quantity;}
    public void setAmount(double amount) {this.amount = amount;}
    public void setPriceAtTime(double priceAtTime) {this.priceAtTime = priceAtTime;}
    public void setDateTime(LocalDate dateTime) {this.dateTime = dateTime;}


    public int getTransactionId() {return transactionId;}
    public int getUserId() {return userId;}
    public TransactionType getType() {return type;}
    public String getAssetSymbol() {return assetSymbol;}
    public double getQuantity() {return quantity;}
    public double getAmount() {return amount;}
    public double getPriceAtTime() {return priceAtTime;}
    public LocalDate getDateTime() {return dateTime;}
}
