package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.service.ChartService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
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

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startGraph();
    }

    private void startGraph() {
        List<String> coins = List.of("BTC", "ETH", "XRP", "DOGE"); // your desired coins
        executor.scheduleAtFixedRate(() -> {
            Map<String, XYChart.Series<String, Number>> dataMap = ChartService.getLiveSeries(coins);

            Platform.runLater(() -> {
                btcChart.getData().setAll(dataMap.get("BTC"));
                ethChart.getData().setAll(dataMap.get("ETH"));
                XRPChart.getData().setAll(dataMap.get("XRP"));
                // add more charts if needed
            });

        }, 0, 10, TimeUnit.SECONDS);

    }
}
