package com.example.walletmanagementsystem.service;

import javafx.scene.chart.XYChart;


import java.time.LocalTime;
import java.util.*;

public class ChartService
{
    private static final int MAX_POINTS = 30;
    private static final Map<String, LinkedList<Double>> priceHistoryMap = new HashMap<>();
    private static final Map<String, LinkedList<String>> timeMap = new HashMap<>();

    public static Map<String, XYChart.Series<String, Number>> getLiveSeries(List<String> coinIds) {
        Map<String, Double> prices = ApiService.getMultiplePrices(coinIds);
        Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();

        String time = LocalTime.now().withNano(0).toString();

        for (String coin : coinIds) {
            double price = prices.getOrDefault(coin, 0.0);
            priceHistoryMap.putIfAbsent(coin, new LinkedList<>());
            timeMap.putIfAbsent(coin, new LinkedList<>());

            LinkedList<Double> priceList = priceHistoryMap.get(coin);
            LinkedList<String> timeList = timeMap.get(coin);

            if (priceList.size() >= MAX_POINTS) {
                priceList.poll();
                timeList.poll();
            }

            priceList.add(price);
            timeList.add(time);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (int i = 0; i < priceList.size(); i++) {
                series.getData().add(new XYChart.Data<>(timeList.get(i), priceList.get(i)));
            }
            seriesMap.put(coin, series);
        }
        return seriesMap;
    }

    public static double getLivePrice(String coinId) {
        List<String> singleCoinList = Collections.singletonList(coinId);
        Map<String, Double> prices = ApiService.getMultiplePrices(singleCoinList);
        return prices.getOrDefault(coinId, 0.0);
    }



}
