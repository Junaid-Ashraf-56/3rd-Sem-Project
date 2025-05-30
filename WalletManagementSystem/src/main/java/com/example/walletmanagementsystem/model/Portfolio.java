package src.model;

import src.model.Asset;
import java.util.HashMap;

public class Portfolio {
    private int portfolioId;
    private int userId;
    HashMap<String,Asset> portfolio = new HashMap<>();

    public Portfolio() {
    }

    public void setPortfolioId(int portfolioId){ this.portfolioId = portfolioId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setPortfolio(HashMap<String, Asset> portfolio) {this.portfolio = portfolio;}


    public int getPortfolioId() {return portfolioId;}
    public int getUserId() {return userId;}
    public HashMap<String,Asset> getPortfolio() {return portfolio;}

}
