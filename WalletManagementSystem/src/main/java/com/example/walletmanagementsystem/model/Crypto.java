package com.example.walletmanagementsystem.model;

public class Crypto extends Asset {
    private String blockchain; // e.g., Ethereum, Bitcoin

    public Crypto(String assetId, String name, double currentPrice, String blockchain) {
        super(assetId, name, currentPrice, AssetType.CRYPTO);
        this.blockchain = blockchain;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }


}

