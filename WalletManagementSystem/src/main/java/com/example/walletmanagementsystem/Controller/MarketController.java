package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.service.ChartService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MarketController implements Initializable {
    @FXML private LineChart<String, Number> MarketChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;


    @FXML private Button BitcoinButton;
    @FXML private Button EthereumButton;
    @FXML private Button XRPButton;
    @FXML private Button BNBButton;
    @FXML private Button SolanaButton;
    @FXML private Button USDTButton;
    @FXML private Button DogeButton;
    @FXML private Button HyperliquidButton;
    @FXML private Button CardanoButton;
    @FXML private Button SuiButton;


    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public XYChart.Series<String, Number> currentSeries = new XYChart.Series<>();
    private final Map<String, XYChart.Series<String, Number>> priceMap = new java.util.HashMap<>();
    private String currentCoin = "bitcoin";

    public void initialize(URL location, ResourceBundle resources) {
        xAxis.setLabel("Time");
        yAxis.setLabel("Price (USD)");
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        MarketChart.setLegendVisible(false);
        MarketChart.setAnimated(false);

        startGraphUpdater();
    }

    private void startGraphUpdater() {
        List<String> coins = List.of(
                "bitcoin", "ethereum", "ripple", "binancecoin",
                "solana", "tether", "dogecoin", "hyperliquid",
                "cardano", "sui"
        );
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
        currentCoin = "ripple";
        updateChart();
    }
    @FXML
    public void onBnbClick() {
        currentCoin = "binancecoin";
        updateChart();
    }
    @FXML
    public void onSolanaClick() {
        currentCoin = "solana";
        updateChart();
    }
    @FXML
    public void onUsdtClick() {
        currentCoin = "tether";
        updateChart();
    }
    @FXML
    public void onDogeClick() {
        currentCoin = "dogecoin";
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
        XYChart.Series<String, Number> series = priceMap.get(currentCoin);
        if (series != null && !series.getData().isEmpty()) {
            MarketChart.getData().setAll(series);

            // Manually adjust Y-axis range if needed
            yAxis.setAutoRanging(true);  // Most important
            xAxis.setAutoRanging(true);
        }
    }

    @FXML
    protected void onClickwalletButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Wallet.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root, 414.0, 383.0);
        stage.setTitle("Wallet!");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }



}