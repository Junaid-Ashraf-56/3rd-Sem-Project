package src.model;

import java.time.LocalDate;

public class Trade {
    private int TradeId;
    private int userId;
    private String assetSymbol;
    private TradeType tradeType;
    private double quantity;
    private double price;
    private LocalDate dateTime;

    // Constructors
    public Trade() {
    }

    public Trade(int tradeId, int userId, String assetSymbol, TradeType tradeType, double quantity, double price, LocalDate dateTime) {
        TradeId = tradeId;
        this.userId = userId;
        this.assetSymbol = assetSymbol;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.price = price;
        this.dateTime = dateTime;
    }

    // Getter and Setter for Trade
    public void setTradeId(int tradeId) {TradeId = tradeId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setAssetSymbol(String assetSymbol) {this.assetSymbol = assetSymbol;}
    public void setTradeType(TradeType tradeType) {this.tradeType = tradeType;}
    public void setQuantity(double quantity) {this.quantity = quantity;}
    public void setPrice(double price) {this.price = price;}
    public void setDateTime(LocalDate dateTime) {this.dateTime = dateTime;}


    public int getTradeId() {return TradeId;}
    public int getUserId() {return userId;}
    public String getAssetSymbol() {return assetSymbol;}
    public TradeType getTradeType() {return tradeType;}
    public double getQuantity() {return quantity;}
    public double getPrice() {return price;}
    public LocalDate getDateTime() {return dateTime;}
}
