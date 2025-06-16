package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.service.ChartService;

import com.example.walletmanagementsystem.utils.Session;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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

    @FXML private Button walletbutton;
    @FXML private Button marketbutton;
    @FXML private Button transactionbutton;
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

    @FXML private Label CoinPrice;
    @FXML private Label CoinName;
    @FXML private Label percentageperhour;
    @FXML private Hyperlink UserName;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public XYChart.Series<String, Number> currentSeries = new XYChart.Series<>();
    private final Map<String, XYChart.Series<String, Number>> priceMap = new java.util.HashMap<>();
    private String currentCoin = "bitcoin";
    private double previousPrice = -1;

    public void initialize(URL location, ResourceBundle resources) {
        xAxis.setLabel("Time");
        yAxis.setLabel("Price (USD)");
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        MarketChart.setLegendVisible(false);
        MarketChart.setAnimated(false);
        CoinName.setText("Bitcoin");

        Platform.runLater(() -> {
            marketbutton.setStyle("-fx-background-color: #f90; -fx-text-fill: white;");
            walletbutton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
            transactionbutton.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
        });
        Platform.runLater(() -> {
            walletbutton.requestFocus();
            UserName.setText(Session.getUsername());
        });
        startGraphUpdater();
        startPriceUpdater();
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
        }, 0, 30, TimeUnit.SECONDS);
    }
    private void startPriceUpdater() {
        executor.scheduleAtFixedRate(() -> {
            double newPrice = ChartService.getLivePrice(currentCoin);

            Platform.runLater(() -> {
                CoinPrice.setText("$ " + String.format("%.2f", newPrice));

                if (previousPrice > 0) {
                    double change = ((newPrice - previousPrice) / previousPrice) * 100;
                    String symbol = change >= 0 ? "+" : "-";
                    percentageperhour.setText(String.format("%s%.2f%% (recent)", symbol, Math.abs(change)));

                    // Set color based on positive/negative
                    if (change >= 0) {
                        percentageperhour.setStyle("-fx-text-fill: #00ff00;"); // green
                    } else {
                        percentageperhour.setStyle("-fx-text-fill: red;");
                    }
                } else {
                    percentageperhour.setText("");
                }

                previousPrice = newPrice;
            });
        }, 0, 30, TimeUnit.SECONDS);
    }




    @FXML
    public void onUsernameClick(ActionEvent event) {
        System.out.println("User profile or settings screen can go here");
    }

    @FXML public void onBitcoinClick() {
        currentCoin = "bitcoin";
        CoinName.setText(" Bitcoin ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }

    @FXML public void onEthereumClick() {
        currentCoin = "ethereum";
        CoinName.setText(" Ethereum ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }

    @FXML public void onXrpClick() {
        currentCoin = "ripple";
        CoinName.setText(" XRP ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }
    @FXML
    public void onBnbClick() {
        currentCoin = "binancecoin";
        CoinName.setText(" BNB ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }
    @FXML
    public void onSolanaClick() {
        currentCoin = "solana";
        CoinName.setText(" Solana ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }
    @FXML
    public void onUsdtClick() {
        currentCoin = "tether";
        CoinName.setText(" Usdt ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }
    @FXML
    public void onDogeClick() {
        currentCoin = "dogecoin";
        CoinName.setText(" Dogecoin");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }
    @FXML
    public void onHyperliquidClick() {
        currentCoin = "hyperliquid";
        CoinName.setText(" Hyper Liquid ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }
    @FXML
    public void onCardanoClick() {
        currentCoin = "cardano";
        CoinName.setText(" Cardano ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
        updateChart();
    }
    @FXML
    public void onSuiClick() {
        currentCoin = "sui";
        CoinName.setText(" SUI ");
        double price = ChartService.getLivePrice(currentCoin);
        CoinPrice.setText("$ " + price);
        previousPrice = -1;
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
    protected void onClickWalletButton(ActionEvent event) throws IOException {
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
    @FXML
    protected void onClickPortfolioButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Portfolio.fxml"));
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

    @FXML
    protected void onClickTransactionButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/walletmanagementsystem/Controller/Transactions.fxml"));
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