package com.example.walletmanagementsystem.model;

import src.model.Role;
import src.model.Wallet;
import src.model.Portfolio;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private Role role;
    private Wallet wallet;
    private Portfolio portfolio;


    // Constructors
    public User() {
    }

    public User(int userId, String name, String email, String password, Role role, Wallet wallet, Portfolio portfolio) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.wallet = wallet;
        this.portfolio = portfolio;
    }

    // Getter and Setter for User
    public void setUserId(int userId) {this.userId = userId;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(Role role) {this.role = role;}
    public void setWallet(Wallet wallet) {this.wallet = wallet;}
    public void setPortfolio(Portfolio portfolio) {this.portfolio = portfolio;}

    public int getUserId() {return userId;}
    public String getName() {return this.name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public Role getRole() {return role;}
    public Wallet getWallet() {return wallet;}
    public Portfolio getPortfolio() {return portfolio;}

}
