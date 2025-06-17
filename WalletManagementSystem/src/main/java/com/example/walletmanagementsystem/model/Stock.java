package com.example.walletmanagementsystem.model;

public class Stock extends Asset {
    private String exchange; // e.g., NASDAQ, NYSE

    public Stock(String assetId, String name, double currentPrice, String exchange) {
        super(assetId, name, currentPrice, AssetType.STOCK);
        this.exchange = exchange;
    }

    public Stock(String assetId, String name, double price, String symbol, double quantity) {
        super(assetId, name, price, AssetType.STOCK);
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}

