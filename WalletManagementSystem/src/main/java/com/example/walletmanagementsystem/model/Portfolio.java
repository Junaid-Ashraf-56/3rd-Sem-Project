package com.example.walletmanagementsystem.model;


import java.util.HashMap;

public class Portfolio {
    private int portfolioId;
    private String accountNumber;
    private int userId;
    HashMap<String,Asset> portfolio = new HashMap<>();

    public Portfolio() {
    }

    public Portfolio(int portfolioId, String accountNumber, int userId, HashMap<String, Asset> portfolio) {
        this.portfolioId = portfolioId;
        this.accountNumber = accountNumber;
        this.userId = userId;
        this.portfolio = portfolio;
    }

    public void setPortfolioId(int portfolioId){ this.portfolioId = portfolioId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setPortfolio(HashMap<String, Asset> portfolio) {this.portfolio = portfolio;}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPortfolioId() {return portfolioId;}
    public int getUserId() {return userId;}
    public HashMap<String,Asset> getPortfolio() {return portfolio;}

}
