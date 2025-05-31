package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.model.Asset;
import javafx.scene.chart.XYChart;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;

public class ChartService {
    private static final int MAX_POINTS = 30;
    private static final Queue<Double> priceHistory = new LinkedList<>();
    private static final Queue<String> timeLabels = new LinkedList<>();

    // ✅ Call periodically (e.g., every 10s) to update graph
    public static XYChart.Series<String, Number> getLivePriceSeries(String coinId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(coinId.toUpperCase());

        // Fetch live price from API
        Asset asset = ApiService.getCrypto(coinId);
        if (asset == null) return series;

        double price = asset.getCurrentPrice();
        String timestamp = LocalTime.now().withNano(0).toString();

        // Store in history (sliding window)
        if (priceHistory.size() >= MAX_POINTS) {
            priceHistory.poll();
            timeLabels.poll();
        }
        priceHistory.add(price);
        timeLabels.add(timestamp);

        // Add to series
        int index = 0;
        for (Double p : priceHistory) {
            String t = ((LinkedList<String>) timeLabels).get(index++);
            series.getData().add(new XYChart.Data<>(t, p));
        }

        return series;
    }
}
