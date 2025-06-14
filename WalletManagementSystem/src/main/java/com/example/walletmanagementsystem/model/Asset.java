package com.example.walletmanagementsystem.model;

public abstract class Asset {
    private String assetId;
    private String name;
    private double currentPrice;
    private AssetType assetType;
    private double quantity;
    private String assetSymbol;


    // Constructors
    public Asset(String assetId, String name, double currentPrice) {
        this.assetId = assetId;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Asset(String assetId, String name, double currentPrice, AssetType assetType) {
        this.assetId=assetId;
        this.name=name;
        this.currentPrice=currentPrice;
        this.assetType=assetType;
    }

    // Getter and Setter for Asset
    public void setAssetId(String assetId) {this.assetId = assetId;}
    public void setName(String name) {this.name = name;}
    public void setCurrentPrice(double currentPrice) {this.currentPrice = currentPrice;}
    public void setAssetType(AssetType assetType) {this.assetType = assetType;}
    public void setQuantity(double quantity) {this.quantity = quantity;}
    public void setAssetSymbol(String assetSymbol) {this.assetSymbol = assetSymbol;}

    public String getAssetId() {return assetId;}
    public String getName() {return name;}
    public double getCurrentPrice() {return currentPrice;}
    public AssetType getAssetType() {return assetType;}
    public double getQuantity() {return quantity;}
    public String getAssetSymbol() {return assetSymbol;}
}
