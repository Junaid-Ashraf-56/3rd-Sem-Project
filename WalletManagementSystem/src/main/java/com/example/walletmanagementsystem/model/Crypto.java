package src.model;

import src.model.Asset;
public class Crypto extends Asset {
    private String blockchain; // e.g., Ethereum, Bitcoin

    public Crypto(String assetId, String name, double currentPrice, String blockchain) {
        super();
        this.blockchain = blockchain;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }
}

