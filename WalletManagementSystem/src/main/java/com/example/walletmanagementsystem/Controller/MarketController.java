package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.service.ChartService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


import javafx.scene.control.Button;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MarketController {
    @FXML public LineChart<String, Number> marketChart;
    @FXML public CategoryAxis xAxis;
    @FXML public NumberAxis yAxis;


    @FXML public Button BitcoinButton;
    @FXML public Button EthereumButton;
    @FXML public Button XRPButton;
    @FXML public Button BNBButton;
    @FXML public Button SolanaButton;
    @FXML public Button USDTButton;
    @FXML public Button DogeButton;
    @FXML public Button HyperliquidButton;
    @FXML public Button CardanoButton;
    @FXML public Button SuiButton;


    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public XYChart.Series<String, Number> currentSeries = new XYChart.Series<>();
    private final Map<String, XYChart.Series<String, Number>> priceMap = new java.util.HashMap<>();
    private String currentCoin = "bitcoin";

    public void initialize(URL location, ResourceBundle resources) {
        startGraphUpdater();
    }

    private void startGraphUpdater() {
        final List<String> coins = List.of("bitcoin", "ethereum", "xrp", "bnb", "solana", "usdt", "doge", "hyperliquid", "cardano", "sui");

        executor.scheduleAtFixedRate(() -> {
            Map<String, XYChart.Series<String, Number>> newData = ChartService.getLiveSeries(coins);

            Platform.runLater(() -> {
                for (String coin : coins) {
                    XYChart.Series<String, Number> updatedSeries = newData.get(coin);

                    // If coin doesn't exist in priceMap, put it
                    priceMap.putIfAbsent(coin, new XYChart.Series<>());
                    XYChart.Series<String, Number> storedSeries = priceMap.get(coin);

                    // Append new data to existing series
                    storedSeries.getData().addAll(updatedSeries.getData());

                    // Limit to 30 data points for better performance
                    if (storedSeries.getData().size() > 30) {
                        storedSeries.getData().remove(0, storedSeries.getData().size() - 30);
                    }
                }

                // Show selected coin
                updateChart();
            });
        }, 0, 10, TimeUnit.SECONDS);
    }

    @FXML public void onBitcoinClick() {
        currentCoin = "bitcoin";
        updateChart();
    }

    @FXML public void onEthereumClick() {
        currentCoin = "ethereum";
        updateChart();
    }

    @FXML public void onXrpClick() {
        currentCoin = "xrp";
        updateChart();
    }
    @FXML
    public void onBnbClick() {
        currentCoin = "bnb";
        updateChart();
    }
    @FXML
    public void onSolanaClick() {
        currentCoin = "solana";
        updateChart();
    }
    @FXML
    public void onUsdtClick() {
        currentCoin = "usdt";
        updateChart();
    }
    @FXML
    public void onDogeClick() {
        currentCoin = "doge";
        updateChart();
    }
    @FXML
    public void onHyperliquidClick() {
        currentCoin = "hyperliquid";
        updateChart();
    }
    @FXML
    public void onCardanoClick() {
        currentCoin = "cardano";
        updateChart();
    }
    @FXML
    public void onSuiClick() {
        currentCoin = "sui";
        updateChart();
    }

    private void updateChart() {
        if (priceMap.containsKey(currentCoin)) {
            marketChart.getData().setAll(priceMap.get(currentCoin));
        }
    }


}
