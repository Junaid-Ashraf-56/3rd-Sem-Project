package com.example.walletmanagementsystem.service;

import com.example.walletmanagementsystem.model.Asset;
import javafx.scene.chart.XYChart;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ChartService {
    private static final int MAX_POINTS = 10;

    private static final Map<String, Queue<Double>> priceMap = new HashMap<>();
    private static final Map<String, Queue<String>> timeMap = new HashMap<>();

    public static XYChart.Series<String, Number> getLivePriceSeries(String coinId) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(coinId.toUpperCase());

        // Fetch asset
        Asset asset = ApiService.getCrypto(coinId);
        if (asset == null) return series;

        double price = asset.getCurrentPrice();
        String timestamp = LocalTime.now().withNano(0).toString();

        // Ensure queue exists
        priceMap.putIfAbsent(coinId, new LinkedList<>());
        timeMap.putIfAbsent(coinId, new LinkedList<>());

        Queue<Double> priceHistory = priceMap.get(coinId);
        Queue<String> timeLabels = timeMap.get(coinId);

        if (priceHistory.size() >= MAX_POINTS) {
            priceHistory.poll();
            timeLabels.poll();
        }

        priceHistory.add(price);
        timeLabels.add(timestamp);

        // Build chart series
        int index = 0;
        for (Double p : priceHistory) {
            String t = ((LinkedList<String>) timeLabels).get(index++);
            series.getData().add(new XYChart.Data<>(t, p));
        }

        return series;
    }
}
