package com.example.walletmanagementsystem;


import com.example.walletmanagementsystem.model.Crypto;
import com.example.walletmanagementsystem.service.ApiService;

public class APItest {
    public static void main(String[] args) {
        Crypto btc = ApiService.getCrypto("bitcoin");
        if (btc != null) {
            System.out.println("ID: " + btc.getBlockchain());
            System.out.println("Name: " + btc.getName());
            System.out.println("Price: $" + btc.getCurrentPrice());
        } else {
            System.out.println("API call failed.");
        }
    }
}

