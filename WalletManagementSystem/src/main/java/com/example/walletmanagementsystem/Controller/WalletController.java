package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.dao.PortfolioDAO;
import com.example.walletmanagementsystem.dao.WalletDAO;
import com.example.walletmanagementsystem.model.Asset;
import com.example.walletmanagementsystem.model.Portfolio;
import com.example.walletmanagementsystem.model.Wallet;
import com.example.walletmanagementsystem.service.ChartService;
import com.example.walletmanagementsystem.utils.Session;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;


import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class WalletController implements Initializable {
    @FXML public LineChart<String, Number> btcChart;
    @FXML public CategoryAxis btcXAxis;
    @FXML public NumberAxis btcYAxis;

    @FXML public LineChart<String,Number> ethChart;
    @FXML public CategoryAxis ethXAxis;
    @FXML public NumberAxis ethYAxis;

    @FXML public LineChart<String,Number> XRPChart;
    @FXML public CategoryAxis XRPXAxis;
    @FXML public NumberAxis XRPYAxis;


    @FXML public VBox walletBalance;
    @FXML public VBox walletCoins;



    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int userId = Session.getUserId();
        showBalance(userId);
        showCoins(userId);

        startGraph();
    }

    private void startGraph() {
        List<String> coins = List.of("bitcoin", "ethereum", "ripple");
        executor.scheduleAtFixedRate(() -> {
            Map<String, XYChart.Series<String, Number>> dataMap = ChartService.getLiveSeries(coins);

            Platform.runLater(() -> {
                btcChart.getData().setAll(dataMap.get("bitcoin"));
                ethChart.getData().setAll(dataMap.get("ethereum"));
                XRPChart.getData().setAll(dataMap.get("ripple"));
            });

        }, 0, 10, TimeUnit.SECONDS);

    }

    /*Dont delete it is for testing purpose */

    public void showBalance(int userId){
        walletBalance.getChildren().clear();
        Wallet wallet = WalletDAO.getWalletById(userId);
        if (wallet!=null){
            Label balanceLabel = new Label("Balance: $" + String.format("%.2f", wallet.getBalance()));
            balanceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            walletBalance.getChildren().add(balanceLabel);
        }
    }

    public void showCoins(int userId) {
        Portfolio portfolio = PortfolioDAO.getPortfolioByUserId(userId);
        if (portfolio != null) {
            HashMap<String, Asset> assets = portfolio.getPortfolio();
            walletCoins.getChildren().clear();

            for (Map.Entry<String, Asset> entry : assets.entrySet()) {
                String symbol = entry.getKey();
                Asset asset = entry.getValue();

                Label coinLabel = new Label(symbol + " - Qty: " + asset.getQuantity());
                coinLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                walletCoins.getChildren().add(coinLabel);
            }
        }
    }

}
