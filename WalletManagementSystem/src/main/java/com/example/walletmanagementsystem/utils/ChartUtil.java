package com.example.walletmanagementsystem.utils;

import javafx.scene.chart.XYChart;

import java.util.List;

public class ChartUtil {
    public static XYChart.Series<String, Number> buildLineChartSeries(String name, List<String> timeLabels, List<Double> prices) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(name);

        for (int i = 0; i < prices.size(); i++) {
            series.getData().add(new XYChart.Data<>(timeLabels.get(i), prices.get(i)));
        }

        return series;
    }
}
