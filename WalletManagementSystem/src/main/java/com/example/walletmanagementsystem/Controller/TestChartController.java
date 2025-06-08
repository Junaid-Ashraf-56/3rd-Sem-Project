package com.example.walletmanagementsystem.Controller;

import com.example.walletmanagementsystem.model.Asset;
import com.example.walletmanagementsystem.service.ApiService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class TestChartController implements Initializable {
    @FXML private LineChart<String, Number> btcChart;
    @FXML private CategoryAxis btcXAxis;
    @FXML private NumberAxis btcYAxis;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final Queue<Double> priceHistory = new LinkedList<>();
    private final Queue<String> timeLabels = new LinkedList<>();
    private static final int MAX_POINTS = 10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btcChart.setAnimated(false);
        btcChart.setLegendVisible(false);
        btcYAxis.setAutoRanging(true);

        executor.scheduleAtFixedRate(() -> {
            Asset btc = ApiService.getCrypto("bitcoin");
            if (btc == null) return;

            double price = btc.getCurrentPrice();
            String time = LocalTime.now().withNano(0).toString();

            if (priceHistory.size() >= MAX_POINTS) {
                priceHistory.poll();
                timeLabels.poll();
            }

            priceHistory.add(price);
            timeLabels.add(time);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            int i = 0;
            for (Double p : priceHistory) {
                String label = ((LinkedList<String>) timeLabels).get(i++);
                series.getData().add(new XYChart.Data<>(label, p));
            }

            Platform.runLater(() -> btcChart.getData().setAll(series));

        }, 0, 10, TimeUnit.SECONDS);
    }
}
