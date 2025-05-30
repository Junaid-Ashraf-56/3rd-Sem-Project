package com.example.walletmanagementsystem.model;

public abstract class Asset {
    private String assetID;
    private String name;
    private double currentPrice;
    private AssetType assetType;
    private double quantity;
    private String assetSymbol;



    public Asset(String assetId, String name, double currentPrice, AssetType assetType) {
    }

    // Constructors



    // Getter and Setter for Asset
    public void setAssetID(String assetID) {this.assetID = assetID;}
    public void setName(String name) {this.name = name;}
    public void setCurrentPrice(double currentPrice) {this.currentPrice = currentPrice;}
    public void setAssetType(AssetType assetType) {this.assetType = assetType;}
    public void setQuantity(double quantity) {this.quantity = quantity;}
    public void setAssetSymbol(String assetSymbol) {this.assetSymbol = assetSymbol;}

    public String getAssetID() {return assetID;}
    public String getName() {return name;}
    public double getCurrentPrice() {return currentPrice;}
    public AssetType getAssetType() {return assetType;}
    public double getQuantity() {return quantity;}
    public String getAssetSymbol() {return assetSymbol;}
}
