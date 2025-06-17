package com.example.walletmanagementsystem.model;

public class Crypto extends Asset {
    private String blockchain; // e.g., Ethereum, Bitcoin

    public Crypto(String assetId, String name, double currentPrice, String blockchain) {
        super(assetId, name, currentPrice, AssetType.CRYPTO);
        this.blockchain = blockchain;
    }

    public Crypto(String assetId, String name, double Price, String symbol, double quantity) {
        super(assetId, name, Price, AssetType.CRYPTO);
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }


}

